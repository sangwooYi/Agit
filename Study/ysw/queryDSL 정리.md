[toc]

### 1. QueryDSL 설정 (메이븐 기준)

`pom.xml`

#### 의존성 추가

 ```xml
 <!-- QueryDSL JPA 라이브러리 -->
 <dependency>
     <groupId>com.mysema.querydsl</groupId>
     <artifactId>querydsl-jpa</artifactId>
     <version>3.6.3</version>
 </dependency>
 
 <!-- 쿼리 타입을 생성할 때 필요한 라이브러리 -->
 <dependency>
     <groupId>com.mysema.querydsl</groupId>
     <artifactId>querydsl-apt</artifactId>
     <version>3.6.3</version>
     <scope>provided</scope>
 </dependency>
 ```



#### 플러그인 추가

```xml
	<build>
		<plugins>
			<plugin>
				<groupId>com.mysema.maven</groupId>
				<artifactId>apt-maven-plugin</artifactId>
				<version>1.1.3</version>
				<executions>
					<execution>
						<goals>
							<goal>process</goal>
						</goals>
						<configuration>
							<outputDirectory>target/generated-sources/java</outputDirectory>
							<processor>com.mysema.query.apt.jpa.JPAAnnotationProcessor</processor>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>

```



### 2. QueryDSL 시작

#### QueryDSL 예시

```java
public void queryDSL(){
    
    // 엔티티 매니저 생성
    EntityManager em = emf.createEntityManager();
    
    // 엔티티 매니저를 JPAQuery 객체에 넘겨 줌 (이래야 QueryDSL 사용 가능)
    JPAQuery query = new JPAQuery(em);
    QMember qMembrer = new QMember("m");  // 별칭 설정, 생성되는 JPQL 별칭이 m인것
    List<Member> members = 
        query.from(qMember)
        	.where(qMember.name.eq("회원1"))
        	.orderBy(qMember.name.desc())
        	.list(qMember);
        /**
        위 QueryDLS 구문은 아래 JPQL문과 같다.
        select m from Member m
        where m.name = ?1
        order by m.name desc
        **/    
}

// 우리 스켈레톤 코드에서는 JPAQuery 대신 JPAQueryFactory 객체를 사용한 듯?
// 아래 코드는 스켈레톤코드에서 UserRepositorySupport.java
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
}
```



#### 기본 Q생성 

우리 스켈레톤 코드에서 QUser.java 가 이 방법!

```java
public class QMember extends EntityPathBase<Member>{
    // 별칭을 직접 지정
    public static final QMember member = new Q Member("member1");
}

//아래와 같이 기본 인스턴스를 사용하는 방법도 존재

import static jpabook.jpashop.domain.QMember.member;

public void basic(){
	
    // EntityManger 인스턴스 생성 -> 엔티티 메니저 인스턴스 이용 JPAQuery 인스턴스 생성
    // JPQQuery 인스턴스를 통해 QueryDSL 작성
    EntityManager em = emf.createEntityManager();
    
    JPAQuery query = new JPAQuery(em);
    List<Member> members =
        query.from(member)
        	.where(member.name.eq("회원1"))
        	.orderBy(member.name.desc())
        	.list(member);
        /**
        위 QueryDLS 구문은 아래 JPQL문과 같다.
        select member from Member member
        where member.name = ?1
        order by member.name desc
        **/       
}
```



### 3. 검색 조건 쿼리

아래와 같이 where 검색 조건을 통해 쿼리문을 가져올 수 있다.

```java
...
    
JPAQuery query = new JPAQuery(em);
QItem item = QItem.item;  //QItem 에서 있는 기본 인스턴스 사용 
List<Item> list = query.from(item)
		/**
		아래와 같이 where 안에 조건 쿼리 설정 가능 아래의 경우는
    	item 중 이름이 "good_product" 인것 중에서 price가 20000 초과인것
    	
    	쿼리 조건 비교는 아래와 같이할 수 있음
    	gt() ~ 초과 (greater then)   goe() ~ 이상 (greater or equal)
    	lt() ~ 미만 (less then	)   loe() ~ 이하 (less or equal)
		**/
    	.where(item.name.eq("good_product").and(item.price.gt(20000)));
    
/**
위 쿼리문은 해당 JPQL문과 동일
select item
from Item item
where  item.name = ?1 and item.price > ?2
**/
    
// 다른 예시

item.price.between(10000, 20000)  // 10000 ~ 20000원 사이의 상품 가격
    
// "상품 1"이라는 문자열을 포함한 이름을 갖는 상품 (SQL에서 '%상품1%')
item.name.contains("상품1") 
    
// 이름이 고급으로 시작하는 상품 (SQL에서 '고급%')
item.name.startWith("고급") 
```



### 4.결과 조회

보통은 `uniqueResult()` 나 `list()` 를 사용하며, 

`com.mysema.query.Projectable` 에 관련 API가 정리 되어 있음

대표적으로는 아래 메서드가 존재

| 메서드명       | 사용 하는 경우                                               | 반환값 없을 때 |
| -------------- | :----------------------------------------------------------- | -------------- |
| uniqueResult() | 조회 결과가 한건일 경우 사용, 반환값이 여러개인 경우 com.mysema.query.NonUniqueResultException 예외 발생 | null 반환      |
| singleResult() | uniqueResult()와 같으나, 반환값이 여러개인 경우 처음 데이터를 반환 | null 반환      |
| list()         | 결과값이 여러개일 경우 사용                                  | 빈 리스트 반환 |



### 5. 페이징과 정렬

```java
...
JPAQuery query = new JPAQuery(em);
QItem item = QItem.item;

List<Item> list = 
    query.from(item)
    // 가격이 20000원 초과인 아이템 중에서,
    // 가격은 내림차순으로 재고량은 오름차순
    // 정렬 된 순서에서 11번째 부터 20개 data 선택
    .where(item.price.gt(20000))
    // desc는 내림차순 asc는 오름차순 
    .orderBy(item.price.desc(), item.stockQuantity.asc())
	.offset(10).limit(20)
    .list(item);

// 아래와 같이 QueryModifiers 를 사용 limit / offset 설정 가능
// 첫번째 인자가 limit, 두번째 인자가 offset에 전달 됨
QueryModifiers queryModifiers = QueryModifiers(20L, 10L);     


// 페이징 처리에는 아래와 같이 listResults()를 list() 대신 사용 한다.

SearchResults<Item> result = 
    query.from(item)
    .where(item.price.gt(10000))
    .offset(10).limit(20)
    .listResults(item);
// 아래와 같이 게터가 지원 됨
long total = result.getTotal();	// 검색된 전체 데이터 수
long limit = result.getLimit();
long offset = result.getOffset();
List<Item> results = result.getResults(); // 조회된 데이터
```



### 6. 그룹

```java
// groupBy()를 사용한다

...    
query.from(item)
    .groupBy(item.price)
    .having(item.price.gt(1000))
    .list(item);
// 아이템의 가격을 group으로 하여, 각 그룹별로 가격이 1000원 초과인 애들만
```



### 7. 조인

```java
// innerJoin, leftJoin, rigjtJoin , fullJoin 사용 가능

Qorder order = Qorder.order;
QMember member =  QMember.member;
QOrderItem orderItem = QOrderItem.orderItem;

query.from(order)
    //첫번째 인자에 조인 대상, 두번 째 인자에 alias 별칭 지정
    .join(order.member, member)
    .leftJoin(order.orderItems, orderItem)
    .list(order);

// 조인 on 사용
query.from(order)
    .leftJoin(order.orderItems, orderItem)
    .on(orderItem.count.gt(2))
    .list(order);

// fetch 조인 사용
query.from(order)
    .innerJoin(oreder.member, member).fetch()
    .leftJoin(order.orderItems, orderItem).fetch()
    .list(order);

// from 절에 여러 조건 사용
Qorder order = Qorder.order;
QMember member =  QMember.member;

query.from(order, member)
    .where(order.member.eq(member))
    .list(order);

```



### 8.서브 쿼리

 ```java
 // 서브 쿼리가 한건이면 unique(), 여러건이면 list()사용
 // JPASubQuery 객체 사용
 QItem item = QItem.item;
 QItem itemSub = new QItem("itemSub");
 
 // 한건일 경우
 query.from(item)
     .where(item.price.eq(
     new JPASubQuery().from(itemSub).unique(itemSub.price.max())
     ))
    .list(item);
 
 // 여러 건 일 경우
 query.from(item)
     .where(item.in(
     	new JPASubQuery().from(itemSub)
         .where(item.name.eq(itemSub.name))
         .list(itemSub)
     ))
     .list(item);
 ```



### 9. 프로젝션과 결과 반환

#### 결과 반환 예시

`프로젝션 : selcet 절에 조회 대상을 지정하는 것`

여러 필드를 반환하는 경우에는, QueryDSL에서는

`com.mysema.query.Tuple` 이라는 MAP과 유사한 Tuple이라는 내부 타입을 사용한다.

조회 결과는 tuple.get() 메서드에 조회한 쿼리 타입을 지정해 주면 됨.

```java
// 프로젝션 대상이 하나
QItem item = QItem.item;
List<String> result = query.from(item).list(item.name);

for (String name : result){
    System.out.println("name = " + name);
}

// 여러 칼럼 반환과 튜플
QItem item = QItem.item;

List<Tuple> result = query.from(item).list(item.name, item.price);

for (Tuple tuple : result){
    System.out.println("name = " + tuple.get(item.name));
    System.out.println("price = " + tuple.get(item.price));
}
```



#### 빈 생성

**쿼리 결과를 엔티티가 아닌, 특정 객체**로 받고 싶으면 사용 한다.

아래 방법들을 제공

프로퍼티 접근 / 필드 직접 접근 / 생성자 사용

아래 ItemDTO에 해당 방법들을 사용

```java
public class ItemDTO{
    
    private String username;
    private int price;
    
    public ItemDTO(){}
    
    publci ItemDTO(String username, int price){
        this.username = username;
        this.price = price;
    }
    
    //Getter, Setter
    public String getUsername(){...}
    public void setUsername(String username){...}
    public int getPrice(){...}
    public void setPrice(int price){...}
}

// 1. 프로퍼티 접근(Setter)
QItem item = QItem.item;
List<ItemDTO> result = query.from(item).list(
    // .bean()메서드로 빈 등록 
    // 첫번째 인자에 사용할 DTO, 두번째 인자에는 쿼리 결과 
    // 여기서 쿼리 결과는 name이나, ItemDTO에서 프로퍼티는 username
    // 따라서 .as("username") 으로 alias 설정
	Projections.bean(ItemDTO.class, item.name.as("username"), ltem.price));


// 2. 필드 직접 접근
QItem item = QItem.item;
List<ItemDTO> result = query.from(item).list(
    // .fields() 메서드 사용
	Projections.fields(ItemDTO.class, item.name.as("username"), ltem.price));


// 3. 생성자 사용
QItem item = QItem.item;
List<ItemDTO> result = query.from(item).list(
    // .constructor() 메서드 사용
	Projections.constructor(ItemDTO.class, item.name, ltem.price));

```



#### DISTINCT

아래와 같이 사용 한다.

```java
query.distinct().from(item)... 
```



### 10. 수정, 삭제 배치 쿼리

수정, 삭제 배치 쿼리를 지원 하나, 영속성 컨텍스트를 무시하고 데이터베이스를

직접 쿼리한 다는 점을 조심 

```java
// 수정 배치 쿼리
QItem item = QItem.item;
// JPAUpdateClause 객체 사용
// 이름이 JPA인 아이템들의 가격을 100원 증가
JPAUpdateClause updateClause = new JPAUpdateClause(em, item);
long count = updateClause.where(item.name.eq("JPA"))
    .set(item.price, item.price.add(100))
    .execute();


// 삭제 배치 쿼리
// JPADeleteClause 객체 사용
QItem item = QItem.item;
JPADeleteClause deleteClause = new JPADeleteClause(em, item);
long count = deleteClause.where(item.name.eq("JPA"))
    .execute();
```





### 11. 동적 쿼리

 `com.mysema.query.BooleanBuilder` 를 사용하여, 특정 조건에 따른 동적 쿼리 생성 가능

```java
// 예문

SearchParam param = new SearchParam();
param.setName("JPA ORM");
param.setPrice("40000");

QItem item = QItem.item;

BooleanBuilder builder = new BooleanBuilder();
// 상품 이름에 따라 있으면 기준 이름 포함하는 아이템 추출
if (StringUtils.hasText(param.getName())){
    builder.and(item.name.contains(param.getName()));
}
// 상품 가격에 따라 기준 가격 초과인 상품들 추출
if (param.getPrice() != null){
    builder.and(item.price.gt(param.getPrice()));
}
List<Item> result = query.from(item)
    .where(builder)
    .list(item);
```



### 12. 메소드 위임

쿼리 타입에 검색 조건을 직접 정의 할 수 있다.

```java
// 검색 조건 정의
public class ItemExpression{
    // QueryDlegate 어노테이션 이용 static 메서드 생성
    @QueryDelegate(Item.class)
    public static BooleanExpression isExpensive(QItem item, Integer price){
        return item.price.gt(price);
    }
}

// 사용할 엔티티 지정
import com.mysema.query.types.expr.BooleanExpression;

public class QItem extends EntityPathBase<Item>{
    ...
    public BooleanExpression isExpensive(Integer price){
        return ItemExpression.isExpensive(this, price);
    }
}

// 실제 사용
query.from(item).where(item.isExpensive(30000)).list(item);
```

