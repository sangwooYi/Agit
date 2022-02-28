<template>
  <el-dialog
    custom-class="invite-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        초 대 하 기
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 150px;">
      <div style="border: 1px solid black; background-color: black; border-radius: 5px; font-size: 20px; padding: 5px 1rem;">
        초대를 원하는 회원의 아이디를 입력해주세요.
      </div>
      <div class="d-flex justify-content-between">
        <div style="width: 100%" class="me-2">
          <el-input placeholder="User@example.com" v-model="state.inputEmail" clearable></el-input>
        </div>
        <div>
          <el-button @click="addTeamMember">초대코드 전송</el-button>
        </div>
      </div>
    </div>

    <!-- footer -->
  </el-dialog>
</template>

<script>
import { reactive, computed, onBeforeMount } from 'vue'
import {useStore} from 'vuex'
export default {
  name: 'invite-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    },
  },
  setup(props, { emit }) {
    const store = useStore();
    const state = reactive({
      dialogVisible: computed(() => props.open),
      inputEmail: '',
      teamId:null,
    })

    const handleClose = function () {
      state.inputEmail = ''
      emit('closeInviteDialog')
    }

    const addTeamMember = function(){
      store.dispatch('root/addTeamMember', { 'teamId': state.teamId, 'userId': state.inputEmail})
      .then(function (result) {
              swal({
                  title: "팀 초대 등록",
                  text: "팀 초대 등록이 완료되었습니다.",
                  icon: "success",
                  button: "확인",
                });

              // router.go(router.currentRoute)




      })
      .catch(function (error) {
        console.log(error.response.data)
        swal({
              title: "팀 초대 등록",
              text: error.response.data.message,
              icon: "error",
              button: "확인",
            });

        handleClose()
      })
    }

    onBeforeMount(() => {
      //state.team.teamId = props.info.teamId;
      console.log(props.info);
      state.teamId = props.info;
      //이번 달 달력 가져오기
      //store.commit()
    })
    return { state, handleClose, addTeamMember }
  }
}
</script>

<style>
.invite-dialog {
  width: 700px;
  height: 300px;
}
</style>
