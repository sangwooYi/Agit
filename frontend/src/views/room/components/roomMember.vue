<template>

  <div id="memberboard" class="col-2 d-flex flex-column justify-content-start" style="border: 1px solid; border-color:#dadadb; border-radius: 10px; margin-top: 45px;margin-bottom: 180px;">
    <div class="d-flex justify-content-center">
      <el-tooltip
        class="box-item"
        effect="dark"
        content="멤버 추가"
        placement="top"
      >
      <i class="fa-solid fa-user-plus member-icon mx-4" @click="state.inviteDialogOpen = true"></i>
      </el-tooltip>
      <el-tooltip
        class="box-item"
        effect="dark"
        content="아지트 참석"
        placement="top"
      >
      <i class="fa-solid fa-chalkboard-user member-icon mx-4" @click="joinConference(state.teamId)"></i>
      </el-tooltip>
    </div>
      <div>
        <!-- <div>멤 버</div> -->
        <p class="mt-3">멤버 목록</p>
        <div v-for="member in state.teamMembers" :key="member.id" class="d-flex align-items-center">
          <div class="d-flex align-items-center mt-2 pointer" style="margin-left:10px" @click="clickChat(member.id)">
            <el-avatar :size="43" :src=member.profileImg></el-avatar>
            <span style="height: 50px; line-height: 50px; margin-left:10px">
              {{ member.nickName }}
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- 초대코드 전송 다이얼로그 -->
  <invite-dialog
    :open="state.inviteDialogOpen"
    :info="state.teamId"
    @closeInviteDialog="onCloseInviteDialog"/>
</template>

<script>
import {reactive, computed, onBeforeMount} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import InviteDialog from './invite-dialog.vue'

export default {
  name:'room-member',
  components:{
    InviteDialog,
  },
  props:{
    teamId:{
      type: Number,
    }
  },
  setup(props){
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      teamMembers: null,
      teamId: computed(()=> props.teamId),
      inviteDialogOpen: false,
      profileinfo:{
        id:'',
        userId:'',
        nickName:''
      },
    })
    function takeMember () {
      const token = store.getters['root/getJWTToken']
      const body = {
        'teamId': state.teamId
      }
      store.dispatch('root/takeMember', {'body': body, 'token': token})
      .then(res=> {
        console.log("takemember!!!!!!!!!!!!!!!!!!!!!!!!!"+res.data)
        state.teamMembers = res.data
        for(let i=0; i<state.teamMembers.length; i++){
          if(state.teamMembers[i].profileImg==null){
            state.teamMembers[i].profileImg = require('@/assets/images/defaultuserprofile.png')
          }
        }
        console.log(state.teamMembers);
        console.log(state.teamMembers[0].name)
      })
      .catch(err => {
        console.log(err)
        // console.log('기달')
      })
    }
    const joinConference = function (roomId) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: roomId
        }
      })
    }

    const onCloseInviteDialog = function () {
      state.inviteDialogOpen = false
    }
    onBeforeMount(()=>{
      takeMember();
    })

    const takeProfile = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        state.profileinfo = res.data
      })
      .catch(err => {
        console.log(err)
      })
    }

    takeProfile()

    const clickChat = function (memberId) {
      const numberA = memberId>state.profileinfo.id?state.profileinfo.id:memberId;
      const numberB = memberId>state.profileinfo.id?memberId:state.profileinfo.id;
      const userId = state.profileinfo.userId;
      const nickName = state.profileinfo.nickName;
      router.push({
        name: 'chatting',
        params : {
          session: numberA+'a'+numberB ,
          userId: userId,
          nickName: nickName,
          roomId : state.teamId,
          }
      })
    }


    return{onBeforeMount, state, joinConference, clickChat, onCloseInviteDialog, takeProfile}
  }
}
</script>

<style>
#memberboard{
  /* background-color: #dfdfdf; */
  color:#747576;
}
.member-icon{
  transform: scale(1.5);
  margin-top: 4vh;
  margin-bottom: 2vh;
  cursor: pointer;
  border-radius: 2vh;
}
.member-icon:hover{
  transform: scale(1.6);
}
.pointer{
  cursor: pointer;
}
</style>
