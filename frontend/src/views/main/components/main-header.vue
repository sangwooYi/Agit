<template>
  <el-row
    class="main-header"
    :gutter="10"
    :style="{ 'height': height }">
    <div class="hide-on-small mb-3">
      <div class="d-flex align-items-center" @click="clickLogo">
        <div class="logo-wrapper"><div class="ic ic-logo"/></div>
        <p id="logo-text">Agit</p>
        <p id="logo-sub">our secret space</p>
      </div>
      <div class="tool-wrapper">
        <!-- <div class="search-field">
          <el-input
            placeholder="검색"
            prefix-icon="el-icon-search"
            v-model="state.searchValue">
          </el-input>
        </div> -->
        <div v-if="state.isLogin === null" class="button-wrapper">
          <p class="text-center" @click="clickSignup">회원가입</p>
          <p class="text-center" @click="clickLogin">로그인</p>
        </div>
          <!-- <el-button type="danger" @click="clickIntro">소개페이지</el-button> -->
          <!-- <el-button type="warning" @click="clickCenter">고객센터</el-button> -->
          <!-- <el-button type="info" @click="clickSchedule">일정관리</el-button> -->
          <!-- <el-button type="success" @click="clickProfile">마이 페이지</el-button> -->
        <div v-else class="button-wrapper">
          <p class="text-center" @click="clickSchedule">마이페이지</p>
          <p class="text-center" @click="clickLogout">로그아웃</p>
          <!-- <el-button type="primary" @click="clickLogout">로그아웃</el-button> -->
        </div>
      </div>

    </div>
    <div class="hide-on-big">
      <div class="menu-icon-wrapper" @click="changeCollapse"><i class="el-icon-menu"></i></div>
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div>
      <div class="menu-icon-wrapper"><i class="el-icon-search"></i></div>
      <div class="mobile-sidebar-wrapper" v-if="!state.isCollapse">
        <div class="mobile-sidebar">
          <div class="mobile-sidebar-tool-wrapper">
            <div class="logo-wrapper"><div class="ic ic-logo"/></div>
            <div v-if="state.isLogin === null">
              <el-button type="primary" class="mobile-sidebar-btn login-btn" @click="clickLogin">로그인</el-button>
              <el-button class="mobile-sidebar-btn register-btn" @click="clickSignup">회원가입</el-button>
            </div>
            <div v-else>
              <el-button type="success" class="mobile-sidebar-btn login-btn" @click="clickProfile">마이 페이지</el-button>
              <el-button type="primary" class="mobile-sidebar-btn register-btn" @click="clickLogout">로그아웃</el-button>
            </div>
          </div>
          <el-menu
            :default-active="String(state.activeIndex)"
            active-text-color="#ffd04b"
            class="el-menu-vertical-demo"
            @select="menuSelect">
            <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index.toString()">
              <i v-if="item.icon" :class="['ic', item.icon]"/>
              <span>{{ item.title }}</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div class="mobile-sidebar-backdrop" @click="changeCollapse"></div>
      </div>
    </div>
  </el-row>
</template>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  props: {
    height: {
      type: String,
      default: '70px'
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      searchValue: null,
      isCollapse: true,
      menuItems: computed(() => {
        const MenuItems = store.getters['root/getMenus']
        let keys = Object.keys(MenuItems)
        let menuArray = []
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {}
          menuObject.icon = MenuItems[keys[i]].icon
          menuObject.title = MenuItems[keys[i]].name
          menuArray.push(menuObject)
        }
        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),
      isLogin: computed(() => store.getters['root/getJWTToken'])
    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      store.commit('root/setMenuActive', param)
      // state.activeIndex = param
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[param]
      })

      if (state.activeIndex === 1 && state.isLogin === null) {
        setTimeout(function() {
          swal({
            title: "로그인이 필요한 서비스입니다.",
            text: "로그인 후 이용해주세요.",
            icon: "error",
            button: "돌아가기",
          });
          store.commit('root/setMenuActive', 0)
          // state.activeIndex = 0
          router.push({
            name: keys[0]
          })
        }, 100)
      }
    }

    const clickLogo = () => {
      store.commit('root/setMenuActive', 0)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[0]
      })
    }

    const clickLogin = () => {
      emit('openLoginDialog')
    }

    const clickSignup = () => {
      emit('openSignupDialog')
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
      store.commit('root/setMenuActive', 0)
      store.commit('root/setUserTeam', [])
      router.push({
        name: 'Intro',
      })
    }

    const clickProfile = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        console.log('정보가져오기')
        console.log(res)
        router.push({
        name: 'Profile',
      })
      })
      .catch(err => {
        console.log(err)
      })
    }

    const clickIntro = function () {
      const getMenus = store.getters['root/getMenus'];
      const commonMenuKeys = Object.keys(getMenus)
      router.push({
        name: commonMenuKeys[0]
      })
    }

    const clickCenter = function () {
      const getMenus = store.getters['root/getMenus'];
      const commonMenuKeys = Object.keys(getMenus)
      router.push({
        name: commonMenuKeys[1]
      })
    }

    const clickSchedule = function () {
      const getMenus = store.getters['root/getMenus'];
      const commonMenuKeys = Object.keys(getMenus)
      router.push({
        name: commonMenuKeys[2]
      })
    }

    const clickChat = function () {
      router.push({
        name: 'chatting'
      })
    }

    const changeCollapse = () => {
      state.isCollapse = !state.isCollapse
    }

    return { state, clickChat, menuSelect, clickLogo, clickLogin, clickSignup, changeCollapse, clickLogout, clickProfile, clickIntro, clickCenter, clickSchedule }
  }
}
</script>
<style>
  .main-header {
    padding: 10px 20px;
  }
  /*Mobile, Tablet*/
  .menu-icon-wrapper {
    display: inline-block;
    vertical-align: top;
    position: relative;
    top: 14px;
  }

  .main-header .hide-on-big .logo-wrapper {
    display: inline-block;
    margin: 0 calc(50% - 51px)
  }
  .main-header .hide-on-big .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/agit_logo.png');
  }
  .mobile-sidebar-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar {
    width: 240px; height: calc(100vh - 1px);
    display: inline-block;
    background-color: white;
    padding: 0 10px;
    vertical-align: top;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-tool-wrapper {
    padding-bottom: 20px;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-wn {
    display: block;
    margin: 0 auto;
    margin-top: 25px;
    height: 30px;
    width: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn.login-btn {
    color: white;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper {
    display: block
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    margin: 0 auto;
    margin-top: 30px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/agit_logo.png');
  }
  .mobile-sidebar-wrapper .mobile-sidebar-backdrop {
    width: calc(100% - 260px); height: calc(100vh - 1px);
    background-color: black;
    display: inline-block;
    opacity: 0.3;
  }
  .mobile-sidebar-wrapper .el-menu{
    margin-top: 0;
    padding-left: 0;
    height: calc(100% - 235px);
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item {
    cursor: pointer;
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item .ic {
    margin-right: 5px;
  }

  /*Desktop - Need to add Class if Need*/
  .hide-on-small{
    width: 100vw;
    display: flex;
  }

  .main-header .hide-on-small .logo-wrapper {
    width: 6vw;
    cursor: pointer;
  }
  .main-header .hide-on-small .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/agit_logo.png');
  }
  .main-header .hide-on-small .tool-wrapper {
    width: 80%;
    display: flex;
    justify-content: end;
  }
  .main-header .hide-on-small .tool-wrapper .button-wrapper {
    float: right;
    display: flex;
  }
  /* .main-header .hide-on-small .tool-wrapper .button-wrapper .el-button {
    width: 120px;
    height: 50px;
    cursor: pointer;
    margin-right: 1%;
  } */
  .main-header .hide-on-small .tool-wrapper .button-wrapper p {
    width: 90px;
    font-size: 15px;
    cursor: pointer;
    margin-top: 15px;
    transform: translate(-40%);
  }
  .main-header .hide-on-small .tool-wrapper .button-wrapper p:hover {
    transform: translate(-40%) scale(0.96);
    color: #3bb8b8;
  }
  .main-header .hide-on-small .tool-wrapper .search-field {
    width: 50%;
    height: 50px;
    max-width: 400px;
    margin-right: 2%;
    display: inline-block;
    background-color: white;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input {
    width: 100%;
    height: 100%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input .el-input__inner {
    width: 88%;
    height: 50px;
    margin-right: 1%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input .el-input__prefix {
    top: 5px;
  }

  .el-overlay {
    background-color: white;
    animation: unfoldIn 1s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
  }

  .select{
    color: #3bb8b8;
  }
  #logo-text{
    font-weight: bold;
    font-size: 35px;
    color: #78ccbb;
    text-shadow: 1px 1px 2px #162d42;
    transform: translate(0, -5%);
    cursor: pointer;
  }
  #logo-sub{
    font-size: 12px;
    color: #78ccbb;
    text-shadow: 0.8px 0.8px 1px #162d42;
    transform: translate(0, 50%);
    cursor: pointer;
  }
  /* .el-overlay .el-overlay-dialog {
    background-color: white;
  } */

  /* .el-overlay .el-overlay-dialog .el-dialog {
    초기 display: none 처럼 크기를 0 으로 하여 보여주지 않음
    transform: scale(0);
    0.5 초 동안 애니메이션을 실행, 단 0.8초 지연시켜 0.8초 후에 애니메이션을 실행
    animation: zoomIn .5s .8s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
  } */


  /* @keyframes unfoldIn {
    0% {
      transform: scaleX(0) scaleY(0.005);
    }
    50% {
      0.5 초간 세로 크기는 작은상태로 가로 너비만 확대
      transform: scaleX(1) scaleY(0.005);
    }
    100% {
      0.5 초간 세로 크기를 확대
      transform: scaleY(1) scaleX(1);
    }
  } */

  /* @keyframes zoomIn {
    from {
      transform: scale(0);
    }
    to {
      transform: scale(1);
    }
  } */

</style>
