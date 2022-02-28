<template>
  <el-dialog
    custom-class="register-team-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >
    <!-- header -->
    <template #title>
      <span>
        아지트 생성
      </span>
    </template>

    <!-- content -->
    <el-form :model="state.form" status-icon :rules="state.rules" ref="registerTeamForm" :label-position="state.form.align">
      <p style="text-align:center;"> 새로운 방에 이름과 아이콘을 부여해 개성을 드러내보세요.</p>
      <p style="text-align:center;">나중에 언제든지 바꿀수 있어요</p>
      <el-upload
        class="avatar-uploader"
        action="https://jsonplaceholder.typicode.com/posts/"
        ref="inputImage"
        :show-file-list="false"
        :before-upload="handleUploadbefore"
        :on-success="handleAvatarSuccess"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <div v-else class="baseicon"><i class="el-icon-plus"></i></div>
        <!-- <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
        <el-icon class="avatar-uploader-icon"></el-icon> -->
  </el-upload>

      <el-form-item prop="teamName" label="방이름" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.teamName" autocomplete="off" id="id-input" placeholder="방 이름을 입력해주세요."></el-input>
      </el-form-item>
      <!-- <el-form-item prop="teamPassword" label="방 비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.teamPassword" autocomplete="off" show-password placeholder="비밀번호를 설정해주세요."></el-input>
      </el-form-item> -->
      <el-form-item prop="teamDescription" label="팀 소개">
        <el-input
          v-model="state.form.teamDescription"
          maxlength="300"
          placeholder="어떤 방인지 간단한 소개를 해주세요."
          show-word-limit
          rows="6"
          type="textarea"
          resize="none"
        >
        </el-input>
      </el-form-item>
      <!-- <el-form-item prop="teamPicture" label="팀 사진" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.teamPicture" autocomplete="off" placeholder="사진 등록 버튼 추가"></el-input>
        <input
            type="file"
            accept="image/*"
            @change="uploadTeamPicture"
            ref="inputImage"
          />
        <span v-if="state.form.teamPicture !== null">
          <img :src="state.form.teamPicture.preview" alt="" height=100>
        </span>
      </el-form-item>
      <el-form-item prop="teamMember" label="팀원 수" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.teamMember" autocomplete="off" placeholder="팀원은 몇명이에요?"></el-input>
      </el-form-item> -->
    </el-form>


    <!-- footer -->
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="registerTeam">아지트 생성</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import jwt_decode from 'jwt-decode'

export default {
  name: 'register-team-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const registerTeamForm = ref(null)
    const inputImage = ref(null)
    const store = useStore()
    const router = useRouter()
    const imageUrl = ref('')
    const state = reactive({
      form: {
        align: 'left',
        teamName: '',
        teamPassword: '',
        teamDescription: '',
        teamPicture: null,
        teamMember: '',
      },
      rules: {
        schedule: [
          { required: true, message: '날짜 선택은 필수입니다.', trigger: 'blur'}
        ],
        content: [
          { required: true, message: '내용 입력은 필수입니다.', trigger: 'blur'}
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '50px',
    })

    const handleClose = function () {
      state.form.teamName = ''
    //  state.form.teamPassword = ''
      state.form.teamDescription = ''
      state.form.teamPicture=''
    //  state.form.teamPicture = null
    //  state.form.teamMember = ''
    inputImage.value.clearFiles()
    imageUrl.value=''
      emit('closeRegisterTeamDialog')
    }

    const registerTeam = function () {
      const token = store.getters['root/getJWTToken']
      const userId = jwt_decode(token).sub
      let formData = new FormData();
      formData.append('teamPicture',state.form.teamPicture);
      formData.append('teamName',state.form.teamName);
      formData.append('teamDescription',state.form.teamDescription)

      store.dispatch('root/registerTeam', { 'formData': formData, 'token': token })
      .then(function (result) {
        swal({
          title: '팀생성',
          text: '새로운 팀이 생성되었습니다',
          icon: 'success',
          button: '확인',
        });
        // 유저가 가입한 방 정보 갱신
        store.dispatch('root/getTeamInfo', token)
        .then(function (result) {
          store.commit('root/setUserTeam', result.data)
          handleClose()
        })
      })
      .catch(function (error) {
        alert('방 생성 실패')
        console.log(error)
      })
    }


    const handleAvatarSuccess  = function() {
      console.log(inputImage.value.uploadFiles[0].raw)
      console.log(URL.createObjectURL(inputImage.value.uploadFiles[0].raw));
      imageUrl.value = URL.createObjectURL(inputImage.value.uploadFiles[0].raw);
    }

    const handleUploadbefore = (file)=>{
      console.log(file.name)
      inputImage.value.clearFiles()
      console.log(inputImage.value.uploadFiles[0].raw)
      console.log(URL.createObjectURL(inputImage.value.uploadFiles[0].raw));
      imageUrl.value = URL.createObjectURL(inputImage.value.uploadFiles[0].raw);
      state.form.teamPicture = inputImage.value.uploadFiles[0].raw;
    }



    return { state, handleClose, registerTeamForm, inputImage, registerTeam,handleAvatarSuccess, handleUploadbefore, imageUrl}
  }
}
</script>

<style>
.register-team-dialog {
  width: 500px !important;
  height: 500px;
}
.register-team-dialog .checkbox .el-form-item__content {
  float: left;
  margin-left: 3rem !important;
}
.register-team-dialog .el-dialog__body {
  padding-bottom: 0px;
}
.register-team-dialog .el-dialog__headerbtn {
  float: right;
}
.register-team-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 300px;
  display: inline-block;
}
.register-team-dialog .el-form-item {
  margin-bottom: 20px;
}
.register-team-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.register-team-dialog .el-input__suffix {
  display: none;
}
.register-team-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.register-team-dialog .dialog-footer .el-button {
  width: 120px;
}
.register-team-dialog .el-checkbox a:hover {
  background-color: rgba(141, 29, 216, 0.322);
}

/* upload image css */
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 100px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  margin: 0px 200px;
  text-align: center;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;

}

.baseicon:hover{
  color: #409eff;
}
.baseicon{
  width: 70px;
  height: 70px;
  color: #8c939d;
}
.el-upload i{
  font-size: 28px;
  vertical-align: bottom;
  font-weight: 900;
  font-style: normal;
  position: absolute;
  left:50%;
  top:50%;
  transform: translate(-50%, -50%);
}
.avatar {
  width: 70px;
  height: 70px;
}

</style>
