<template>
  <el-row
    class="main-sidebar"
    :gutter="10"
    :style="{ 'width': width, 'height': 'calc(100vh - 120px)' }">
    <div class="hide-on-small">
      <el-menu
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        style="width:80px; height:100%; z-index:3"
        @select="menuSelect">
        <!-- <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index">
          <i v-if="item.icon" :class="['ic', item.icon]"/>
          <span>{{index}} {{ item.title }}</span>
          <div v-if="index === 4" style="border-top: 1px solid #dcdfe6;"/> -->
          <!-- <hr v-if="index === 4" style="color: black;"> -->
          <!-- <hr v-if="index === 4" style="color: black"> -->
        <!-- </el-menu-item> -->

        <div>
          <div style="padding-left: 10px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #cfcfcf; width: 90%; margin: 1rem auto 0;">
            <p style="margin-bottom: 0px" class="ms-2 mt-1">팀 목록</p>
          </div>
        </div>
        <div style="width: 100%;">
          <div style="height: 57px; display: flex; flex-direction: flex-column; justify-content: center; align-items: center; width: 100%; margin-left:4px">
            <el-tooltip
              effect="dark"
              placement="top"
            >
              <template #content>
                <div style="font-size: 15px; width: 90px; text-align: center;">
                  방 생성하기
                </div>
              </template>
              <el-button @click="registerTeam" id="btnCreateRoom" circle>
                <i class="el-icon-plus"></i>
                <!-- <p style="font-size: 40px;">+</p> -->
              </el-button>
            </el-tooltip>
          </div>
        </div>
        <el-scrollbar id="sidebar">

          <el-menu-item :class="{active: getActive(index)}" id="roomMenuItem" v-for="(item, index) in state.userTeams" :key="index + state.menuItems.length" :index="index + state.menuItems.length">
            <el-tooltip
              class="box-item"
              effect="dark"
              :content=item.teamName
              placement="right"
            >
            <div style="justify-content: center; align-items: center; width: 90%; margin: 0 auto;">
              <el-avatar :size="43" :src=item.teamPicture></el-avatar>
              </div>
            </el-tooltip>
            <!-- <p>{{item.id}}{{item.teamName}}!!</p> -->
          </el-menu-item>
          <!-- <el-button @click="state.registerTeamDialogOpen = true">방 생성 버튼</el-button> -->
        </el-scrollbar>

      </el-menu>
    </div>
  </el-row>


</template>
<style>
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;
}
.main-sidebar .hide-on-small {
  height: 100%;
}
.main-sidebar .hide-on-small .el-menu {
  height: 100%;

}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: 1px;
}
.main-sidebar .el-menu .el-menu-item .ic {
  margin-right: 5px;
}
#sidebar {
  height: calc(100% - 100px);
}
/* #btnCreateRoom {
  text-align: center;
  border: 1px solid #89bdb2b3;
  background-color: #a7f3e3c7;
  width: 43px;
  height: 43px;
}
#btnCreateRoom i {
  text-shadow:
    -1px -1px 0 #89bdb2b3,
    1px -1px 0 #89bdb2b3,
    -1px 1px 0 #89bdb2b3,
    1px 1px 0 #89bdb2b3;
  font-weight: 900;
  font-style: normal;
  color: white;
} */
.tool-tip {
  font-size: 500px;

}


#btnCreateRoom {
  position: relative;
  display: inline-block;
  text-align: center;
  width: 43px;
  height: 43px;
  background-color: #78ccbb;
  /* border-radius: 50%; */
}

#btnCreateRoom:hover {
  border-radius: 30%;
  border: 1px solid #b9a23190;
  background-color: #f9db49;
  transition: 0.1s;
}

#btnCreateRoom:hover i {
  text-shadow:
    -1px -1px 0 #b9a23190,
    1px -1px 0 #b9a23190,
    -1px 1px 0 #b9a23190,
    1px 1px 0 #b9a23190;
  color: #78ccbb;
}

#btnCreateRoom i {
  /* text-shadow:
    -1px -1px 0 #f9db49,
    1px -1px 0 #f9db49,
    -1px 1px 0 #f9db49,
    1px 1px 0 #f9db49; */
  /* background-color: grey; */
  font-weight: 900;
  font-style: normal;
  color: #f9db49;
}

#btnCreateRoom:active {
  /* animation-duration: 1s; */
  /* animation-name: btnClick; */
  transform: translateY(3px);
}

#roomMenuItem:hover{
  background-color: #fff !important;
  border-left:10px solid #36393f;
  border-radius: 10px;
}

.el-avatar:active{
  transform: translateY(3px);
}

.el-avatar{
  background-color: #fff;
}


#roomMenuItem:focus{
  box-sizing: border-box;
  border-left:10px solid #36393f;
  border-radius: 10px;

}

.active{
  box-sizing: border-box;
  border-left:10px solid #36393f;
  border-radius: 10px;
}
.el-scrollbar__bar {
  /* background-color: black;
  --el-scrollbar-background-color: black; */
  display: none;
}

</style>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
// import jwt_decode from 'jwt-decode'



export default {
  name: 'main-sidebar',
  props: {
    width: {
      type: String,
      default: '240px'
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      searchValue: null,
      // registerTeamDialogOpen: false,
      isLogin: computed(() => store.getters['root/getJWTToken']),
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

        // const TeamItems = store.getters['root/getTeams']
        // console.log('유저가 가입한 팀 정보 라우터에 추가')
        // keys = Object.keys(TeamItems)
        // for (let i = 0; i < TeamItems.length; i++) {
        //   let menuObject = {}
        //   menuObject.icon = TeamItems[keys[i]].teamPicture
        //   menuObject.title = TeamItems[keys[i]].teamName
        //   menuArray.push(menuObject)
        // }
        // console.log('방 정보 리스트')
        // console.log(menuArray)
        return menuArray
      }),

      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),
      userTeams: computed(function(){
        let userTeams = store.state.root.userTeams;
        for(let i=0; i<store.state.root.userTeams.length; i++){
          console.log(userTeams[i]);
          if(store.state.root.userTeams[i].teamPicture==null){
            userTeams[i].teamPicture = require('@/assets/images/agit_logo.png')
          }else{
             userTeams[i].teamPicture = 'http://localhost:8080/api/v1/team/profileimg/'+userTeams[i].id;
          }
        }
        return userTeams;
      } ),
      circleUrl: require('@/assets/images/agit_logo.png'),


      selectedIndex:-1,

    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      // store.commit('root/setMenuActive', param)
      console.log('방 클릭')
      console.log(param)

      // state.activeIndex = param
      // const MenuItems = [store.getters['root/getMenus'], store.getters['root/getTeams']]
      const getMenus = store.getters['root/getMenus']
      const getTeams = store.getters['root/getTeams']
      // console.log('방 정보')
      // console.log(getMenus[0])
      // console.log(getTeams)
      const MenuItems = store.getters['root/getMenus']
      // const MenuItems = Object.assign({}, getMenus, getTeams)
      // console.log(MenuItems)
      // let keys = Object.keys(MenuItems)
      const commonMenuKeys = Object.keys(MenuItems)
      state.selectedIndex = param - commonMenuKeys.length
      if (param < commonMenuKeys.length) {
        router.push({
          name: commonMenuKeys[param]
        })
      } else {
        const key = param - commonMenuKeys.length
        console.log(getTeams[key.toString()])
        const roomId = getTeams[key.toString()].id
        // const roomName = getTeams[key.toString()].teamName
        // const roomDescription = getTeams[key.toString()].teamDescription
        // const roomPicture = getTeams[key.toString()].teamPicture
        // console.log(roomId)
        router.push({
          name: 'room-board',
          params: {
            roomId: roomId,
            // roomName: roomName,
            // roomDescription: roomDescription,
            // roomPicture: roomPicture,
          },
        })
      }

      // if (state.activeIndex === 1 && state.isLogin === null) {
      //   setTimeout(function() {
      //     swal({
      //       title: "로그인이 필요한 서비스입니다.",
      //       text: "로그인 후 이용해주세요.",
      //       icon: "error",
      //       button: "돌아가기",
      //     });
      //     store.commit('root/setMenuActive', 0)
      //     // state.activeIndex = 0
      //     router.push({
      //       name: keys[0]
      //     })
      //   }, 100)
      // }
    }

    // 방 생성하기
    const registerTeam = () => {
      emit('openRegisterTeamDialog')
    }

    const onCloseRegisterTeamDialog = function () {
      // state.registerTeamDialogOpen = false
    }

    const onRegisterSuccess = function () {
      console.log('adsjfoiasdjfoiasdjfaspjdf')
      console.log(state.userTeams)
    }
    const getActive = function(index){
      if(index==state.selectedIndex){
        return true;
      }else{
        return false;
      }
    }

    return { state, menuSelect, registerTeam, onCloseRegisterTeamDialog, onRegisterSuccess, getActive }
  }
}
</script>
