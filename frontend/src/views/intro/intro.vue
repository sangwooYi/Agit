<template>
  <div id="container">
    <img :src="require('../../assets/images/agit_logo.png')" id="intro-logo">
    <div class="hello">
      <!-- 회원가입, 로그인 버튼 -->
      <div class="d-flex justify-content-end" style="margin-right: 250px;">
        <el-row :gutter="20">
          <!-- v-if문으로 수정 예정 -->
          <div v-if="state.isLogin === null" class="button-wrapper d-flex flex-row" style="position:fixed; top:35px; right:70px">
            <!-- <el-button @click="clickSignup" style="background-color:#112; color: white;">회원가입</el-button> -->
            <!--<el-button @click="clickAuth" style="background-color:#112; color: white;">인증w</el-button>-->
            <!-- <el-button @click="clickLogin" style="background-color:#112; color: white;">로그인</el-button> -->
            <p @click="clickSignup" class="intro-btn">회원가입</p>
            <p @click="clickLogin" class="intro-btn">로그인</p>
          </div>
          <div v-else class="button-wrapper d-flex flex-row" style="position:fixed;top:35px; right:70px">
            <p @click="clickHome" class="intro-btn">홈으로</p>
            <p @click="clickLogout" class="intro-btn">로그아웃</p>
            <!-- <el-button @click="clickHome" style="background-color:#112; color: white;">홈으로</el-button>
            <el-button @click="clickLogout" style="background-color:#112; color: white;">로그아웃</el-button> -->
          </div>
         </el-row>
      <!-- 로고(네온사인)!! -->
      <br><br><br>
      </div>
      <div class="logo" id="neon" style="width: 100%; height: 35vh;">
        <b><span>a</span><span>g</span>i<span>t</span></b>
      </div>
      <br><br><br>
      <!-- card 1 -->
      <div
        data-aos="fade-left"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto; margin-top: 30px; z-index: -1; padding-top: 0px;"
      >
        <img
          src="https://cdn.pixabay.com/photo/2019/09/02/15/58/family-4447538_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
        <div class="card-body" style="margin-left:80px">
          <h5 class="card-title" style="margin-top: 48%">아지트(Agit) 소개 <i class="fas fa-home" style="color: #FFD732"></i></h5>
          <p class="card-text" style="color: #808080; width:100%;">
            아지트는 가족, 연인, 친구들의 아름다운 추억을 간직하기 위한 서비스를 제공합니다.
          </p>
          <p class="card-text" style="color: #808080; width:100%;">
            다양한 추억을 공유해보세요.
          </p>

        </div>
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

      <div
        data-aos="fade-right"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"

      >
        <div class="card-body" style="margin-right:100px">
          <h5 class="card-title" style="margin-top: 48%">한 눈에 보는 일정 관리 <i class="fas fa-book" style="color: #4646CD"></i></h5>
          <p class="card-text" style="color: #808080">
            나의 스케줄을 한 눈에 확인 해보세요.
          </p>


        </div>
        <img
          src="https://cdn.pixabay.com/photo/2019/05/15/18/53/agenda-4205694_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

       <div
        data-aos="fade-left"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto; margin-top: 150px; z-index: -1"

      >
        <img
          src="https://cdn.pixabay.com/photo/2016/06/29/21/14/women-1487825_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
        <div class="card-body" style="margin-left:80px">
          <h5 class="card-title" style="margin-top: 48%">온라인 모임 <i class="fas fa-smile-wink" style="color: #FF6A89"></i></h5>
          <p class="card-text" style="color: #808080; width:100%;">
            화상으로 반가운 얼굴들을 만날 수 있습니다.
          </p>


        </div>
      </div>

      <br><br><br><br><br><br>
      <!-- 공백은 br 6개 -->

      <div
        data-aos="fade-right"
        class="card d-flex  flex-row border border-white"
        style="width: 70%; height: 100%; margin-left: auto; margin-right: auto;  margin-top: 150px; z-index: -1"

      >
        <div class="card-body" style="margin-right:100px">
          <h5 class="card-title" style="margin-top: 48%">나만의 추억 앨범 <i class="fas fa-heart" style="color:red;"></i></h5>
          <p class="card-text" style="color: #808080">
            사진, 동영상, 일정 등을 앨범에 담아보세요.
          </p>



        </div>
        <img
          src="https://cdn.pixabay.com/photo/2019/12/16/10/45/postage-stamps-4699092_960_720.jpg"
          class="card-img-top"
          alt="..."
          style="width:60%"
        />
      </div>
    </div>

    <br><br><br><br>


    <div id="final">
      <div
        data-aos="zoom-out"
        style="width: 18rem; margin: 100px auto;"
        class="d-flex flex-column align-items-center"

      >
        <img
            src="https://st.depositphotos.com/2086879/2449/i/450/depositphotos_24490551-stock-photo-old-paper.jpg"
            alt="..."
            class="my-4"
          />
        <div style="position: absolute; margin-top:10%;" class="d-flex flex-column align-items-center">
          <!-- <div v-if="state.isLogin === null" class="button-wrapper"> -->
            <!-- <el-button @click="clickSignup" style="background-color:white; color: #ffc107; font-family: 'East Sea Dokdo', cursive;">회원가입</el-button> -->
            <!-- <el-button @click="clickLogin " style="background-color:white; color: #ffc107;">로그인</el-button> -->
          <!-- </div> -->
          <div style="margin-top: 20px; font-size: 60px; font-family: 'East Sea Dokdo', cursive;">Agit Invitation</div>
          <p style="font-family: 'East Sea Dokdo', cursive; font-size: 30px;">비밀의 공간으로 당신을 초대합니다.</p>
          <div style="width:90%; height: 90%; margin-top: 12%;" class="d-flex flex-column align-items-center">
            <div class="candle" style="margin-top: 10px;">
              <span class="glow"></span>
              <span class="flames"></span>
              <span class="thread"></span>
            </div>
            <!-- <div v-else class="button-wrapper">
              <el-button @click="clickProfile" style="background-color:white; color: #ffc107;">마이 페이지</el-button>
              <el-button @click="clickLogout" style="background-color:white; color: #ffc107;">로그아웃</el-button>
            </div> -->
          </div>


        </div>
      </div>
    </div>
  </div>


</template>




<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'
import AOS from 'aos';
import 'aos/dist/aos.css';
import { reactive, computed } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Intro',
  setup (props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),
      isLogin: computed(() => store.getters['root/getJWTToken'])
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      AOS.init({
        duration: 1200,
      })
      // AOS.refresh()
      store.commit('root/setMenuActiveMenuName', 'intro')

    })

    const clickLogin = () => {
      emit('openLoginDialog')
    }

    const clickSignup = () => {
      emit('openSignupDialog')
    }

    const clickAuth = () => {
      emit('openAuthDialog')
    }

    const clickLogout = function () {
      swal({
            title: "정상적으로 로그아웃 되었습니다.",
            text: "다음에 또 이용해주세요.",
            icon: "success",
            button: "확인",
          });
      store.commit('root/setJWTTokenReset')
      localStorage.removeItem('JWT')
      store.commit('root/setMenuActive', 2)
      router.push({
        name: 'Intro',
      })
    }

    const clickHome = function () {
      router.push({
        name: 'schedule',
      })
      // const token = store.getters['root/getJWTToken']
      // store.dispatch('root/getProfile', token)
      // .then(res => {
      //   console.log('정보가져오기')
      //   console.log(res)
      // })
      // .catch(err => {
      //   console.log(err)
      // })
    }
    // 인트로 페이지 방문시 최상단으로 스크롤 이동
    const scrollTop = function (){
      window.scrollTo(0, 0)
    }
    scrollTop()
    return { state, clickLogin, clickSignup, clickAuth, clickLogout, clickHome, scrollTop}
  }
}
</script>

<style scoped>
#intro-logo{
  margin-top: 4px;
  position: absolute;
  margin-left: 60px;
  width: 85px;
  height: 65px;
}

@import url('https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Noto+Sans+KR:wght@300&display=swap');
#container {
  position: absolute;
  top: -90px;
  left: -90px;
  margin-left: 0;
  margin-top: 0;
  z-index: 10;
  background-color: white;
  width: 111.1%;

  /* background: #112; */
  /* background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1"); */
  background-size:cover;
}

#neon {
  background: #112;
  background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1");
  /* background-size:cover; */
}

#final {
  background: #112;
  background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1");
  background-size:cover;
}

#invite {
  background-image: url('http://www.public.asu.edu/~msimshau/GIT237/scroll.jpg');
  background-size:cover;
}

@import url(//fonts.googleapis.com/css?family=Vibur);
html,body{
  height:100%
}
/* .hello {
  background: #112 url(//images.weserv.nl/?url=i.imgur.com/6QJjYMe.jpg)  center no-repeat;
  background-size:cover;
  margin:0;
  z-index: -3;
} */
.intro-btn{
  font-size: 17px;
  width: 5.5vw;
  transform: translate(0, -50%);
  cursor: pointer;
}
.intro-btn:hover{
  color: #3bb8b8;
  transform: scale(0.95) translate(0, -50%);
}
.logo {
  text-align: center;
  width: 65%;
  height: 250px;
  margin: auto;
  position: relative;
  /* margin-top: 0px; */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
   user-select: none;
}

.logo b{
  font: 400 19vh "Vibur";
  color: #fee;
  text-shadow: 0 -40px 100px, 0 0 2px, 0 0 1em #FFEB5A, 0 0 0.5em #FFEB5A, 0 0 0.1em #FFEB5A, 0 10px 3px #000;
}
.logo b span{
  animation: blink linear infinite 2s;
  font: 400 19vh "Vibur";
}
.logo b span:nth-of-type(2){
  animation: blink linear infinite 3s;
  font: 400 19vh "Vibur";
}
@keyframes blink {
  78% {
    color: inherit;
    text-shadow: inherit;
  }
  79%{
     color: #333;
  }
  80% {

    text-shadow: none;
  }
  81% {
    color: inherit;
    text-shadow: inherit;
  }
  82% {
    color: #333;
    text-shadow: none;
  }
  83% {
    color: inherit;
    text-shadow: inherit;
  }
  92% {
    color: #333;
    text-shadow: none;
  }
  92.5% {
    color: inherit;
    text-shadow: inherit;
  }
}

.candle {
    width: 10em;
    height: 10em;
    font-size: 7px;
    background: linear-gradient(
        orange,
        darkorange,
        sienna,
        saddlebrown 50%,
        rgba(0, 0, 0, 0.6)
    );
    box-shadow:
        inset 2em -3em 5em rgba(0, 0, 0, 0.4),
        inset -2em 0 5em rgba(0, 0, 0, 0.4);
    border-radius: 10em / 4em;
    position: relative;
    display: flex;
    justify-content: center;
    top: 10em;
}

.candle::before {
    content: '';
    position: absolute;
    width: inherit;
    height: 2em;
    border: 0.2em solid darkorange;
    border-radius: 50%;
    box-sizing: border-box;
    background: radial-gradient(
        #444,
        orange,
        saddlebrown,
        sienna,
        darkorange
    );
    filter: opacity(0.7);
}

.thread {
    position: absolute;
    width: 0.6em;
    height: 3.6em;
    top: -1.8em;
    background: linear-gradient(
        #111,
        black,
        orange 90%
    );
    border-radius: 40% 40% 0 0;
}

.flames {
    position: absolute;
    width: 2.4em;
}

.flames::before {
    content: '';
    position: absolute;
    width: inherit;
    height: 6em;
    background-color: royalblue;
    top: -4.8em;
    border-radius: 50% 50% 35% 35%;
    border: 0.2em solid dodgerblue;
    box-sizing: border-box;
    filter: opacity(0.7);
}

.flames::after {
    content: '';
    position: absolute;
    width: inherit;
    height: 12em;
    top: -12em;
    background: linear-gradient(white 80%, transparent);
    border-radius: 50% 50% 20% 20%;
    box-shadow: 0 -0.6em 0.4em darkorange;
    animation:
        enlarge 5s linear infinite,
        move 6s linear infinite;
}

@keyframes move {
    0%, 100% {
        transform: rotate(2deg);
    }

    50% {
        transform: rotate(-2deg);
    }
}

@keyframes enlarge {
    0%, 100% {
        height: 12em;
        top: -12em;
    }

    50% {
        height: 14em;
        top: -13em;
    }
}

.glow {
    position: absolute;
    width: 10em;
    height: 18em;
    background-color: orangered;
    border-radius: 50%;
    top: -17em;
    filter: blur(6em);
    animation: okay 100ms infinite;
}

@keyframes okay {
    to {
        filter: blur(6em) opacity(0.8);
    }
}



</style>
