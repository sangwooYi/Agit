<template>
  <!-- <el-container class="main-wrapper">
    <main-header
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog"
      @openSignupDialog="onOpenSignupDialog"
      style="position: fixed; top: 0; background-color: white;"
      />
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="240px">
        <main-sidebar
          :width="`240px`"/>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <main-footer :height="`110px`"/>
  </el-container> -->
  <!-- <div class="main-wrapper"> -->
    <main-header
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog"
      @openAuthDialog="onOpenAuthDialog"
      @openSignupDialog="onOpenSignupDialog"

      style="position:fixed; top: 0; background-color: white;"
      />
    <div class="main-container" style="position: fixed; top: 70px;">
      <div class="hide-on-small" width="90px;" style="height: calc(100vh - 150px);">
        <main-sidebar
          :width="`90px`"
          style="position : fixed; z-index:10;"
          @openRegisterTeamDialog="onOpenRegisterTeamDialog"/>
      </div>
    </div>
    <main-footer
      :height="`80px`"
      style="position:fixed; bottom: 0; width: 100%; background-color: white;"/>
  <!-- </div> -->
  <div style="position: absolute; top: 90px; left: 90px; width: 90%;">
    <router-view @openLoginDialog="onOpenLoginDialog" @openSignupDialog="onOpenSignupDialog" @openAuthDialog="onOpenAuthDialog" @openRegisterTeamDialog="onOpenRegisterTeamDialog" :key="$route.fullPath"></router-view>

  </div>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
  <signup-dialog
    :open="signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog"/>
  <auth-dialog
    :open="authDialogOpen"
    @closeAuthDialog="onCloseAuthDialog"/>
  <register-team-dialog
    :open="registerTeamDialogOpen"
    @closeRegisterTeamDialog="onCloseRegisterTeamDialog"/>
  <signup-birthday
    :open="signupBirthdayOpen"
    @closeSignupBirthday="onCloseSignupBirthday"/>

</template>
<style>
  /* @import "https://unpkg.com/element-plus@1.0.2-beta.44/lib/theme-chalk/index.css"; */
  /* @import "https://unpkg.com/element-plus@1.1.0-beta.10/dist/index.css"; */
  @import "https://unpkg.com/element-plus@1.1.0-beta.10/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';

   *{font-family: 'Jua', sans-serif;}


</style>
<script>
//import LoginDialog from './components/login-dialog'
import LoginDialog from './components/login-dialog'
import SignupDialog from './components/signup-dialog'
import AuthDialog from './components/auth-dialog.vue'
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import RegisterTeamDialog from './components/register-team-dialog'
import SignupBirthday from './components/signup-birthday'
import { useStore } from 'vuex'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter,
    LoginDialog,
    SignupDialog,
    RegisterTeamDialog,
    AuthDialog,
    SignupBirthday,
  },
  data () {
     return {
      loginDialogOpen: false,
      signupDialogOpen: false,
      authDialogOpen: false,
      registerTeamDialogOpen: false,
      signupBirthdayOpen: false,
    }
  },
  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
      console.log('로그인 다이얼로그 켜기')
      console.log(this.loginDialogOpen)
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
      console.log('로그인 다이얼로그 끄기')
      console.log(this.loginDialogOpen)
    },
    onOpenSignupDialog () {
      this.signupDialogOpen = true
    },
    onCloseSignupDialog () {
      this.signupDialogOpen = false
    },
    onOpenAuthDialog  () {
      this.authDialogOpen = true
    },
    onCloseAuthDialog () {
      this.authDialogOpen = false
    },
    onOpenRegisterTeamDialog () {
      this.registerTeamDialogOpen = true
    },
    onCloseRegisterTeamDialog () {
      this.registerTeamDialogOpen = false
    },
    onCloseSignupBirthday () {
      this.signupBirthdayOpen = false
    }
  },
  created () {
    // 이미 토큰이 있는 경우 방 정보 미리 불러오기
    const store = useStore()
    const token = localStorage.getItem('JWT')
    if (token !== null) {
      store.dispatch('root/getTeamInfo', token)
      .then(function (result) {
        store.commit('root/setUserTeam', result.data)
      })

      store.dispatch('root/getProfile', token)
      .then(function (result) {
        store.commit('root/setNickName', result.data.nickName)
      })
    }
  }
}
</script>
