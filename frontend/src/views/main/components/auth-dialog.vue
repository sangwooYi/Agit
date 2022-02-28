<template>
  <el-dialog custom-class="signup-dialog" v-model="state.dialogVisible" @close="handleClose" :destroy-on-close="true" :close-on-click-modal="false" :close-on-press-escape="false" top="100px">
    <!-- header -->
    <template #title>
      <span>
        인 증
      </span>
    </template>

    <!-- content -->
    <el-form :model="state.form" status-icon :rules="state.rules" ref="signupForm" :label-position="state.form.align">
      <el-form-item prop="email" label="이메일" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.id" autocomplete="off" style="width: 70%" :disabled="state.form.isValidatedId" id="id-input" placeholder="이메일"></el-input>
        <el-button size="small" style="float: right; margin-top:5px;" @click="sendAuthEmail" :disabled="state.form.isValidatedId">인증번호 보내기</el-button>
      </el-form-item>
      <el-form-item prop="authNumber" label="인증번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.authNumber" autocomplete="off" show-authNumber placeholder="인증번호"></el-input>
      </el-form-item>



    </el-form>

    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickAuthup" v-loading.fullscreen="loading">인증체크</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style>
.signup-dialog {
  width: 500px !important;
  height: 550px;
}
.signup-dialog .checkbox .el-form-item__content {
  float: left;
  margin-left: 3rem !important;
}
.signup-dialog .el-dialog__body {
  padding-bottom: 0px;
}
.signup-dialog .el-dialog__headerbtn {
  float: right;
}
.signup-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 300px;
  display: inline-block;
}
.signup-dialog .el-form-item {
  margin-bottom: 20px;
}
.signup-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.signup-dialog .el-input__suffix {
  display: none;
}
.signup-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.signup-dialog .dialog-footer .el-button {
  width: 120px;
}
.signup-dialog .el-checkbox a:hover {
  background-color: rgba(141, 29, 216, 0.322);
}

</style>
<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'auth-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const signupForm = ref(null)
    const router = useRouter()
    const loading = ref(false)
    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      auth: {

      },
      form: {
        id: '',
        authNumber: '',
      },
      dialogVisible: computed(() => props.open),
      isAvailable: computed(function () {
        return signupForm.value.validate((valid) => valid)
      }),
      formLabelWidth: '120px',
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    const clickAuthup = function () {
      // 인증 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      if (!state.form.isValidatedId) {
        swal({
          title: '다시 확인 해주세요.',
          icon: 'error',
          button: '확인'
        })
      } else {
        signupForm.value.validate((valid) => {
          if (valid) {
            // 날짜는 년, 월, 일로 구분하여 대입
            loading.value = TextTrackCue
            if(state.form.authNumber==state.auth.str){
              //모달 다음 페이지로 이동
              console.log("인증 성공");
              setTimeout(() => {
                loading.value = false
                swal({
                  title: "인증 성공",
                  text: "회원가입을 진행해주세요.",
                  icon: "success",
                  button: "확인",
                });
              }, 500)
            }


          } else {
            swal({
              title: "회원가입 실패",
              text: "필수 항목을 입력해주세요.",
              icon: "error",
              button: "돌아가기",
            })
          }
        });
      }
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.authNumber = ''
      emit('closeAuthDialog')
    }

    const clickCustomercenter = function () {
      store.commit('root/setMenuActive', 2)
      handleClose()
    }

    const sendAuthEmail = function () {
        store.dispatch('root/getAuthNumber', { userEmail: state.form.id })
        .then(res => {
          state.form.isValidatedId = true
          state.auth = res.data;
          swal({
              title: "인증번호를 성공적으로 보냈습니다.",
              icon: "success",
              button: "확인",
            });
        })
        .catch(err => {
          swal({
              title: "인증번호 보내기 오류입니다.",
              icon: "error",
              button: "확인",
            });
        })

    }

    return { signupForm, state, clickAuthup, handleClose, clickCustomercenter, sendAuthEmail, loading }
  }
}
</script>
