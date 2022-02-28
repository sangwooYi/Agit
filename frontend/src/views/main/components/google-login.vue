<template>
<div>
  <h1>IsInit: {{ Vue3GoogleOauth.isInit }}</h1>
  <h1>IsAuthorized: {{ Vue3GoogleOauth.isAuthorized }}</h1>
  <h2 v-if="user">signed user: {{user}}</h2>
  <img :src="imageurl" width="50" height="50"/>
  <p> 로그인 사용자 이름 : {{name}}</p>
  <button @click="handleClickGetAuthCode" :disabled="!Vue3GoogleOauth.isInit">get authCode</button>
  <button @click="handleClickSignOut" :disabled="!Vue3GoogleOauth.isAuthorized">sign out</button>
  <button @click="handleClickDisconnect" :disabled="!Vue3GoogleOauth.isAuthorized">disconnect</button>
  <button
          @click="subwaymarker"
          class='googlebtn'
        >

        </button>
  <el-row class="mb-4">
    <el-button type="primary" @click="handleClickSignIn" :disabled="!Vue3GoogleOauth.isInit || Vue3GoogleOauth.isAuthorized">구글로 로그인하기</el-button>
  </el-row>

</div>
</template>

<script>

import { inject, toRefs } from 'vue';
import qs from 'qs';
import $axios from 'axios';
export default {
  name: 'google-login',
  props: {
    msg: String,
  },
  data(){
    return {
      user: '',
      name:'',
      email:'',
      imageurl:'',
      id:'',
      idToken:'',
    }
  },
  methods: {
    async handleClickSignIn(){
      try {
        const googleUser = await this.$gAuth.signIn();
        if (!googleUser) {
          return null;
        }
        const authuser = await this.$gAuth.instance;
        console.log('authuser', authuser);
        console.log('googleUser', googleUser);
        this.user = googleUser.getBasicProfile().getEmail();
        this.name = googleUser.getBasicProfile().getName();
        this.imageurl = googleUser.getBasicProfile().getImageUrl();
        this.id = googleUser.getBasicProfile().getId();
         console.log('getId', this.user);
         console.log('getBasicProfile', await googleUser.getBasicProfile());
         console.log('userImage', this.imageurl)
         console.log('userName', this.name);
         console.log('userId', this.id);
         console.log('getBasicProfile', googleUser.getBasicProfile());
        console.log(
          'getAuthResponse',
          this.$gAuth.instance.currentUser.get().getAuthResponse()
        );
        this.idToken = this.$gAuth.instance.currentUser.get().getAuthResponse().id_token;
        this.tokenVerify()
      } catch (error) {
        //on fail do something
        console.error(error);
        return null;
      }
    },
    async handleClickGetAuthCode(){
      try {
        const authCode = await this.$gAuth.getAuthCode();
        console.log('authCode', authCode);
      } catch(error) {
        //on fail do something
        console.error(error);
        return null;
      }
    },
    async handleClickSignOut() {
      try {
        await this.$gAuth.signOut();
        console.log('isAuthorized', this.Vue3GoogleOauth.isAuthorized);
        this.user = '';
      } catch (error) {
        console.error(error);
      }
    },
    handleClickDisconnect() {
      window.location.href = `https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=${window.location.href}`;
    },
    tokenVerify(){
      const url='auth/token';
      const params = new URLSearchParams();
      params.append('idToken', this.idToken);
      console.log(this.idToken);
      $axios.post(url,{accessToken:this.idToken}).then((res)=>{
        console.log(res);
      }).catch((error)=>{
        console.log(error);
      }).then(()=>{
        console.log('tokenVerify End!!');
      })
    }

  },
  setup(props) {
    const { isSignIn } = toRefs(props);
    const Vue3GoogleOauth = inject('Vue3GoogleOauth');
    const handleClickLogin = () => {};
    return {
      Vue3GoogleOauth,
      handleClickLogin,
      isSignIn,
    };


  },
};
</script>
<style>
button {
  display: inline-block;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  background: #fff;
  border: 1px solid #dcdfe6;
  color: #606266;
  -webkit-appearance: none;
  text-align: center;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  outline: 0;
  margin: 0;
  -webkit-transition: 0.1s;
  transition: 0.1s;
  font-weight: 500;
  padding: 12px 20px;
  font-size: 14px;
  border-radius: 4px;
  margin-right: 1em;
}
button:disabled {
  background: #fff;
  color: #ddd;
  cursor: not-allowed;

}
.googlebtn{
  width: 40px;
    height: 40px;
    background-size: contain;
    background-repeat: no-repeat;
   background-image: url('../../../assets/images/googleicon.png');
}

</style>
