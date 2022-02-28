<template>
  <!-- <p>{{ info }}</p> -->
  <el-dialog
    custom-class="nickname-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
    style="height: 150px"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        닉네임 수정
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 100px;">
      <el-form :model="state.form" status-icon :rules="state.rules" :label-position="state.form.align">
        <el-form-item prop="inputNickname" label="닉네임" :label-width="state.formLabelWidth" >
          <el-input v-model="state.form.inputNickname" autocomplete="off" placeholder="Nickname"></el-input>
        </el-form-item>
      </el-form>
      <div class="d-flex justify-content-end">
        <div>
          <el-button @click="editNickname">닉네임 수정</el-button>
        </div>
      </div>
    </div>

    <!-- footer -->
    <!-- <template #footer>
      <el-button @click="editNickname">닉네임 수정</el-button>
    </template> -->
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
      },
      rules: {
        inputNickname: [
          { required: true, message: '닉네임을 입력해주세요.', trigger: 'blur' },
          { min: 2, max: 10, message: '2 ~ 10자 이내로 해주세요.', trigger: 'change' },
          { pattern: /^[a-zA-zㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,10}$/, message: '한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)'}
        ],
      }
    })

    const handleClose = function () {
      state.form.inputNickname = ''
      emit('closeNicknameDialog')
    }

    const editNickname = function () {
      if (state.form.inputNickname.length >= 2) {
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
      } else {
        swal({
            title: "닉네임 수정 실패",
            text: "올바르게 닉네임을 입력해주세요",
            icon: "error",
            button: "확인",
          });
      }
    }






    return { state, handleClose,  editNickname }
  }

}
</script>

<style>
  .nickname-dialog {
    width: 700px;
    height: 200px !important
  }
</style>
