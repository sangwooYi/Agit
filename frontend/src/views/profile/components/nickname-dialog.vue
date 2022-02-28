<template>
  <!-- <p>{{ info }}</p> -->
  <el-dialog
    custom-class="nickname-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        닉네임 수정
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 150px;">
      <div style="border: 1px solid black; background-color: black; border-radius: 5px; font-size: 20px; padding: 5px 1rem;">
        수정할 닉네임을 적어주세요.
      </div>
      <div class="d-flex justify-content-between">
        <div style="width: 100%" class="me-2">
          <el-input placeholder="ex)멋진 홍길동" v-model="state.form.inputNickname" clearable></el-input>
        </div>
        <div>
          <el-button @click="editNickname">닉네임 수정</el-button>
        </div>
      </div>
    </div>
    <!-- footer -->
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'nickname-dialog',
  emits: ['edit-nickname'],
  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    }
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      form : {
        inputNickname: null,
      }
    })

    const handleClose = function () {
      state.form.inputNickname = ''
      emit('closeNicknameDialog')
    }

    const editNickname = function () {
      if (state.form.inputNickname) {
        emit('edit-nickname', {
          nickname: state.form.inputNickname,
        })
        const token = store.getters['root/getJWTToken']
        // console.log(props.info.password)
        const body = {
          'userId': props.info.userId,
          // 'day': props.info.day,
          // 'month': props.info.month,
          // 'name': props.info.name,
          // 'password': props.info.password,
          // 'year': props.info.year,
          'birthDay': props.info.birthDay,
          'nickName': state.form.inputNickname,
        }
        console.log(body)
        console.log("다이얼로그")
        store.dispatch('root/editProfile',{ 'body': body, 'token': token})
        // console.log(props.info.cdate)
        .then(res => {
          setTimeout(() => {
                swal({
                  title: "닉네임 수정",
                  text: "닉네임이 성공적으로 수정되었습니다..",
                  icon: "success",
                  button: "확인",
                });
              }, 500)

              // router.go(router.currentRoute)


              handleClose()
        })
        .catch(err => {
          console.log(err)
        })
      }
    }






    return { state, handleClose,  editNickname }
  }

}
</script>

<style>
  .nickname-dialog {
    width: 700px;
    height: 300px;
  }
</style>
