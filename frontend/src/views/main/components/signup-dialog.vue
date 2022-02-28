<template>
  <el-dialog custom-class="signup-dialog" v-model="state.dialogVisible" @close="handleClose" :destroy-on-close="true" :close-on-click-modal="false" :close-on-press-escape="false" top="50px" style="width:100%">
    <!-- header -->
    <template #title>
      <span>
        회 원 가 입
      </span>
    </template>

    <!-- content -->
    <el-form :model="state.form" status-icon :rules="state.rules" ref="signupForm" :label-position="state.form.align">
      <el-form-item prop="id" label="이메일" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.id" autocomplete="off" style="width: 70%" :disabled="state.form.isValidatedId" id="id-input" placeholder="email"></el-input>
        <el-button size="small" style="float: right; margin-top:5px;" @click="checkDup" :disabled="state.form.isValidatedId" v-loading.fullscreen="loading">중복 확인</el-button>
        <!-- <el-button size="small" style="float: right; margin-top:5px;" @click="sendAuthEmail" :disabled="state.form.isValidatedAuth">인증 번호 전송</el-button> -->

      </el-form-item>
      <el-form-item prop="authNumber" label="인증번호" ref="authForm" :label-width="state.formLabelWidth">
        <el-input id="auth-number" v-model="state.form.authNumber" autocomplete="off" style="width:70%" :disabled="state.form.isValidatedAuth" show-authNumber placeholder="인증번호"></el-input>
        <el-button size="small" style="float: right; margin-top:5px;" @click="clickAuthup" :disabled="state.form.isValidatedAuth">인증 체크</el-button>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password placeholder="Password" id="password"></el-input>
      </el-form-item>
      <el-form-item prop="passwordConfirm" label="비밀번호 재확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.passwordConfirm" autocomplete="off" show-password placeholder="Password Confirmation" id="password-confirmation"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.name" autocomplete="off" placeholder="Name"></el-input>
      </el-form-item>
      <el-form-item prop="birthDate" label="생년월일" :label-width="state.formLabelWidth" >
        <el-date-picker
          style="width: 100%;"
          v-model="state.form.birthDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="Birthday"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="nickname" label="별명" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.nickname" autocomplete="off" placeholder="Nickname"></el-input>
      </el-form-item>

    </el-form>

    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickSignup" v-loading.fullscreen="loading">회원가입</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<style>
.signup-dialog {
  /* transform: translate(0, -10%) !important; */
  width: 500px !important;
  height: 550px !important;
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

.signup-dialog .el-checkbox a {
  color: #0d6efd;
  text-decoration: underline;
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
  name: 'signup-dialog',

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
    const authForm = ref(null)
    const router = useRouter()
    const loading = ref(false)

    // 비밀번호 확인 유효성 검사
    // 비밀번호(password)와 비밀번호 재확인(passwordConfirm)의 일치 유무 판별
    const validatePass = (rule, value) => {
      if (value !== state.form.password) {
        return new Error("비밀번호가 일치하지 않습니다.")
      } else {
        return true
      }
    }

    // 약관 동의 유무 검사
    const validateAgree = (rule, value) => {
      if (!value) {
        return new Error("약관에 동의해주세요.")
      } else {
        return true
      }
    }
    //인증번호 인증 유무 검사
    const validateAuth = (rule, value) => {
      if (!value) {
        return new Error("이메일 인증을 해주세요.")
      } else {
        state.form.isPossibleAuth = true
        return true
      }
    }

    const validateId = (rule, value) => {
      console.log(value.length)
      if (!value || !value.trim()) {
        return new Error("아이디를 입력해주세요.")
      }// else if (!/^[a-zA-Z0-9]+$/.test(value)) {
       // return new Error("아이디는 영문 대 소문자, 숫자로만 구성할 수 있습니다.")
      //} else if (value.length < 5 || value.length > 16) {
        //return new Error("아이디의 길이는 5 ~ 16자 이내로 해주세요.")
      //}
      else {
        state.form.isPossibleId = true
        return true
      }
    }

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      form: {
        auth: {

        }, //백엔드에서 인증 번호를 받아와서 저장하는 변수
        isPossibleId: false, // id 규정에 맞는지 확인하는 변수. true가 된다면 중복검사를 받아도 된다는 의미.
        isValidatedId: false, // 해당 id로 회원가입이 가능한지 확인하는 변수. true가 된다면 회원가입 가능, false면 이미 존재하는 id
        isValidatedAuth: false, //인증번호 인증했는지 아닌지 알려주는 변수
        agreement: false, // 약관에 동의했는지 확인하는 변수
        id: '',
        password: '',
        authNumber:'',
        passwordConfirm: '',
        name: '',
        nickname: '',
        align: 'left',
        birthDate: '',
      },
      rules: {
        id: [
          { required: true, message: '아이디를 입력해주세요.', trigger: 'blur' },
          // { min: 5, max: 16, message: '아이디의 길이는 5 ~ 16자 이내로 해주세요.', trigger: 'change' },
          { validator: validateId, trigger: 'blur'}
        ],
        password: [
          { required: true, message: '비밀번호를 입력해주세요.', trigger: 'blur' },
          { pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{5,16}$/, message: '5 ~ 16자 영문, 숫자, 특수문자를 혼합하여 사용해주세요.'}
        ],
        authNumber:[
          { required: true, message: '인증번호를 입력해주세요.', trigger: 'blur' },
          { validator: validateAuth, trigger: 'blur'}
        ],
        passwordConfirm: [
          { required: true, message: '필수 정보입니다.', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' },
        ],
        name: [
          { required: true, message: '이름을 입력해주세요.', trigger: 'blur' },
          // { min: 2, max: 10, message: '2 ~ 10자 이내로 해주세요.', trigger: 'change' },
          { pattern: /^[a-zA-zㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,10}$/, message: '한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)'}
        ],
        nickname: [
          { required: true, message: '별명을 입력해주세요.', trigger: 'blur' },
          // { min: 2, max: 10, message: '2 ~ 10자 이내로 해주세요.', trigger: 'change' },
          { pattern: /^[a-zA-zㄱ-ㅎ|ㅏ-ㅣ|가-힣\d]{2,10}$/, message: '한글, 영문(대, 소문자)와 숫자를 사용하세요. (특수기호, 공백 사용 불가)'}
        ],
        birthDate: [
          { required: true, message: '생년월일을 선택해주세요.', trigger: 'blur'}
        ],
        agreement: [
          { validator: validateAgree, message: '약관을 동의해주세요.'},
        ]
      },
      dialogVisible: computed(() => props.open),
      isAvailable: computed(function () {
        return signupForm.value.validate((valid) => valid)
      }),
      isAvailableAuth: computed(function () {
        return authForm.value.validate((valnum) => valnum)
      }),
      formLabelWidth: '120px',
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })
    const clickAuthup = function () {
      // 인증 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      if (!state.form.isPossibleAuth) {
        swal({
          title: '다시 확인 해주세요.',
          icon: 'error',
          button: '확인'
        })
        state.form.isPossibleAuth = false
      } else {
        if(state.form.authNumber==state.auth.str){
          console.log("일치 확인");
          document.getElementById("password").focus()
          swal({
                  title: "인증 성공",
                  text: "회원가입을 진행해주세요.",
                  icon: "success",
                  button: "확인",
                });
          state.form.isValidatedAuth = true
        }
        //console.log("확인");
        // authForm.value.validate((valnum) => {
        //   if (valnum) {
        //     loading.value = TextTrackCue
        //     if(state.form.authNumber==state.auth.str){
        //       //모달 다음 페이지로 이동
        //       console.log("인증 성공");
        //       state.form.isValidatedAuth = true;
        //       setTimeout(() => {
        //         loading.value = false
        //         swal({
        //           title: "인증 성공",
        //           text: "회원가입을 진행해주세요.",
        //           icon: "success",
        //           button: "확인",
        //         });
        //       }, 500)
        //     }


        //   } else {
        //     swal({
        //       title: "인증 실패",
        //       text: "필수 항목을 입력해주세요.",
        //       icon: "error",
        //       button: "돌아가기",
        //     })
        //   }
        // });
      }
    }

    const clickSignup = function () {
      // 회원가입 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      if (!state.form.isValidatedId) {
        swal({
          title: '아이디 중복 확인을 해주세요.',
          icon: 'error',
          button: '확인'
        })
      }else if(!state.form.isValidatedAuth){
        swal({
          title: '인증번호 체크 확인을 해주세요',
          icon: 'error',
          button: '확인'
        })
      }
      else {
        signupForm.value.validate((valid) => {
          if (valid) {
            // 날짜는 년, 월, 일로 구분하여 대입
            loading.value = true
            const arr = state.form.birthDate.split("-")
            const year = arr[0]
            const month = arr[1]
            const day = arr[2]
            // store.dispatch('root/requestRegister', { userId: state.form.id, password: state.form.password, name: state.form.name, year: year, month: month, day: day, nickName: state.form.nickname })
            store.dispatch('root/requestRegister', { userId: state.form.id, password: state.form.password, name: state.form.name, birthDay: state.form.birthDate, nickName: state.form.nickname })
            .then(function (result) {
              console.log('여기서 걸림')
              // console.log('로딩 스피너 넣으면 됨')
              setTimeout(() => {
                loading.value = false
                swal({
                  title: "아지트의 일원이 되신 것을 축하합니다.",
                  text: "로그인 후 이용해주세요.",
                  icon: "success",
                  button: "확인",
                });
              }, 500)

              // router.push({
              //   name: 'schedule',
              // })

              handleClose()
            })
            .catch(function (error) {
              loading.value = false
              console.log('회원가입 실패')
            })
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
      state.form.password = ''
      state.form.passwordConfirm = ''
      state.form.name = ''
      state.form.nickname = ''
      state.form.agreement = false
      state.form.birthDate = ''
      state.form.isValidatedId = false
      state.form.isValidatedAuth = false
      state.form.isPossibleId = false
      state.form.isPossibleAuth = false
      emit('closeSignupDialog')
    }

    const clickCustomercenter = function () {
      store.commit('root/setMenuActive', 2)
      handleClose()
    }

    const checkDup = function () {
      if (!state.form.isPossibleId) {
        swal({
            title: "아이디를 확인해주세요.",
            text: "5 ~ 16자 사이의 영문 대 소문자, 숫자로만 구성되어야 합니다.",
            icon: "error",
            button: "확인",
          });
        state.form.isPossibleId = false
      } else {
        loading.value = true
        store.dispatch('root/checkDupId', { userId: state.form.id })
        .then(res => {
         // state.form.isValidated = true
        //  swal({
          //    title: "사용가능한 아이디입니다.",
            //  icon: "success",
            //  button: "확인",
         //   });
         //   document.getElementById("auth-number").focus()
            sendAuthEmail();
        })
        .catch(err => {
          swal({
              title: "이미 존재하는 아이디입니다.",
              icon: "error",
              button: "확인",
            });
          loading.value = false
        })
      }
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
	  loading.value = false
        })
        .catch(err => {
          swal({
              title: "인증번호 보내기 오류입니다.",
              icon: "error",
              button: "확인",
            });
  	   loading.value = false
        })

    }

    return { signupForm, state, authForm, clickAuthup, clickSignup, handleClose, clickCustomercenter, checkDup, loading ,sendAuthEmail}
  }
}
</script>
