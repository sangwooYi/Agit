## 폴더별 분석

`generated 폴더`

`queryDSL`문을 사용할 때 필요한 query 객체를 정의하는 폴더로 이해했습니다.

```java
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user")
```



`db/entity 폴더`

JPA 를 위한 모델을 정의하는 폴더입니다. Django를 기준으로 하면 models.py의 역할을 하는 공간이며,

DB table에 저장할 스키마 구조를 지정합니다.

이 스켈레톤 코드에서는 모든 필드들이 기본적으로 가져야 하는 Primary Key값처럼

공통 필드가 존재할 경우 `BaseEntity` 클래스에 작성한뒤 이를 상속하여 사용하도록 구현 되어있습니다.

그리고 이때 `lombok`  3rd party 라이브러리에 의해, 게터 세터를 `@Getter` `@Setter` 어노테이션 만으로

지정 가능한것을 확인 할 수 있습니다.

```java
... 
    
/**
 * 모델 간 공통 사항 정의.
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id = null;
}

//이를 상속하여 사용
@Entity
@Getter
@Setter
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(
        name = "USER_ID_UNIQUE", columnNames = {"userId"}
)})
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    String userId;
    String name;
    String nickName;
    int year;
    int month;
    int day;
    @CreatedDate
    LocalDateTime cDate;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

}
```



`db/Repository 폴더`

JPA를 위한 공간으로 기본적으로는 스켈레톤 코드 기준으로 이야기를 하면

`UserRepository` 클래스에서는 `JpaRepository` 를 상속받으며, `.save()` `.delete()`등

기본적인 JPA 쿼리 메서드를 사용 가능하며, 필요한 경우

`@Query("select m from m where m.name like %:name")` 같이 Query 어노테이션을

이용하여 JPQL문을 구현 할 수있습니다.

그리고 queryDSL문을 사용하고 싶은 경우 `UserRepositorySupport` 처럼 추가 클래스를 구현하며

 여기서 위에서 생성한 queryDsl 타입을 정의한 클래스 객체를 사용하게 됩니다.



`controller 폴더` 

특정 url에 따라 수행할 컨트롤러 역할을 수행하는 공간으로 이때 `service` 폴더에 정의한

메서드들을 사용하게 됩니다.

이때 `request` 폴더에 정의한 클래스를 `DTO` 처럼 사용하는 구조로 스켈레톤 코드가 작성되어 있어,

이부분은 차주에 다른 DB 테이블이 추가되고, 기능이 확장 되기 전에,

 이 구조를 유지할지 변경할지 한번더 논의를 하기로 이야기가 되었습니다.





## jwt 설정

기본적으로 `JwtAuthenticationfilter` 와 `JwtTokenUtil` 을 통해, jwt 발급 구현이 이미 되어있음을

확인 했습니다. 그리고 `SecurityConfig` 클래스에서 발급한 토큰을 인증할 url을 지정해주면,

요청으로 받은 header에 토큰이 맞는지 체크하는 로직까지 구현되어 있음을 확인하였고,

실제로  토큰이 정상 발행 및 인증처리가 이루어짐을 확인하였습니다.