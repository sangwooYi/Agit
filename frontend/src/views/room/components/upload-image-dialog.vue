<template>
  <el-dialog
    custom-class="upload-image-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >
    <!-- header -->
    <template #title>
      <span>
        사 진 추 가
      </span>
    </template>

    <!-- content -->
    <div>
      <div>
        사진을 추가해보세요.
      </div>
      <div>
        <el-form :model="state.form" :rules="state.rules" ref="uploadImageForm" :label-position="state.form.align">
          <el-form-item prop="schedule" label="일자">
            <el-date-picker
              style="width: 100%;"
              v-model="registerDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
              disabled
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item prop="content" label="내용">
          </el-form-item>
          <input
            id="uploadImage"
            type="file"
            accept="image/*"
            multiple
            @change="selectedImage"
            ref="inputImage"
          />
          <el-form-item prop="df" style="height: 140px;">
            <div v-if="state.form.images.length > 5">
              <!-- <div v-for="i in 5" :key="i">
                <img :src="state.form.images[i].preview" alt="" width=100 height=100 class="me-1">
              </div> -->
              <img v-for="i in 5" :key="i" :src="state.form.images[i].preview" alt="" width=100 height=100 class="me-1">
              <p class="d-flex justify-content-center align-items-center">
                외 {{ state.form.images.length - 5}}개의 이미지
              </p>
            </div>
            <div v-else>
              <div v-for="img in state.form.images" :key="img.name">
                <img :src="img.preview" alt="" width=100 height=100>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <!-- footer -->
    <template #footer>
      <div>
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="uploadImage">사진등록</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
export default {
  name: 'upload-image-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    },
    info:{
      type:Object,
      required:true,
    },
    registerDate: {
      type: String,
      default: '1970-01-01'
    }
  },

  setup(props, { emit }) {
    const uploadImageForm = ref(null)
    const inputImage = ref(null)
    const store = useStore();
    const state = reactive({
      form: {
        align: 'left',
        images: [],
        schedule: computed(() => props.registerDate),
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
      isLogin: computed(()=> store.getters['root/getJWTToken'])
    })

    const handleClose = function () {
      state.form.content = ''
      state.form.images = []
      document.getElementById('uploadImage').value = ''
      emit('closeUploadImageDialog')
    }

    const selectedImage = function () {
      console.log('이미지등록')
      for (let i = 0; i < inputImage.value.files.length; i++) {
        console.log(URL.createObjectURL(inputImage.value.files[i]))
        console.log(inputImage.value);
        console.log(state.form.images)
        state.form.images.push({
          file: inputImage.value.files[i],
          preview: URL.createObjectURL(inputImage.value.files[i])
        })

      }
    }

      const uploadImage = function(){
        let formData = new FormData();
        for (let i=0; i<state.form.images.length; i++){
          formData.append('upfile', state.form.images[i].file);
        }
        formData.append('uploadDate',state.form.schedule);
        formData.append('teamId',props.info)

        store.dispatch('root/uploadImage',{ 'formData': formData, 'token': state.isLogin})
        .then(res => {
          setTimeout(() => {
                swal({
                  title: '사진 등록',
                  text: '사진이 일정에 등록되었습니다.',
                  icon: 'success',
                  button: '확인',
                });
              }, 500)

              // router.go(router.currentRoute)
              console.log(res)
              // console.log(swal)
              emit('createImage')
              handleClose()
          })
          .catch(err => {
            console.log(err)
          })
      }

    return { state, handleClose, uploadImageForm, inputImage, selectedImage, uploadImage }
  }
  }
</script>

<style>
.upload-image-dialog {
  width: 700px;
  height: 500px !important;
}
img{
  float: left;
}
</style>
