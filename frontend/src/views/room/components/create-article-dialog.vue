<template>
  <el-dialog
    custom-class="create-article-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
    :destroy-on-close="true"
  >
    <!-- header -->
    <template #title>
      <span>
        게 시 글 작 성
      </span>
    </template>

    <!-- content -->
    <div>
      <div>
        글을 작성해보세요.
      </div>
      <div>
        <el-form :model="article" :rules="state.rules" ref="createArticleForm" :label-position="state.form.align">
          <el-form-item prop="uploadDate" label="날짜">
            <el-date-picker
              style="width: 100%;"
              v-model="article.uploadDate"
              type="date"
              v-if="isUpdated"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
              disabled
            >
            </el-date-picker>
            <el-date-picker
              style="width: 100%;"
              v-model="state.form.uploadDate"
              v-else
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
              disabled
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item prop="title" label="제목" :label-width="state.formLabelWidth" >
            <el-input v-model="article.title" autocomplete="off" placeholder="제목을 입력해주세요."></el-input>
          </el-form-item>
          <el-form-item prop="content" label="본문" :label-width="state.formLabelWidth">
            <el-input
              v-model="article.content"
              maxlength="500"
              placeholder="내용을 입력해주세요."
              show-word-limit
              rows="10"
              type="textarea"
              resize="none"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <!-- footer -->
    <template #footer>
      <span v-if="isUpdated">
        <el-button @click="handleClose">취소</el-button>
        <el-button type="info" @click="updateArticle">게시글 수정</el-button>
      </span>
      <span v-else>
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="createArticle">게시글 등록</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import jwt_decode from 'jwt-decode'

export default {
  name: 'create-article-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
    },
    isUpdated : {
      type: Boolean,
      default: false
    },
    registerDate: {
      type: String,
      default: '1970-01-01'
    },
    article: {
      type: Object,
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const createArticleForm = ref(null)

    const state = reactive({
      form: {
        align: 'left',
        content: '',
        title: '',
        uploadDate: computed(() => props.registerDate),
      },
      rules: {
        uploadDate: [
          { required: true, message: '날짜 선택은 필수입니다.', trigger: 'blur'}
        ],
        title: [
          { required: true, message: '제목 입력은 필수입니다.', trigger: 'blur'}
        ],
        content: [
          { required: true, message: '내용 입력은 필수입니다.', trigger: 'blur'}
        ]
      },
      dialogVisible: computed(() => props.open),
    })

    const handleClose = function () {
      emit('closeCreateArticleDialog')
      props.article = {
        content: '',
        createdTime: '',
        id: '',
        index: '',
        nickName: '',
        teamName: '',
        title: '',
        updatedDate: '',
        uploadDate: '',
        writer: '',
      }
    }

    const createArticle = function () {
      const token = store.getters['root/getJWTToken']
      const writer = jwt_decode(token).sub
      const teamId = props.info.teamId
      const teamName = props.info.teamName
      const title = props.article.title
      const content = props.article.content
      const uploadDate = state.form.uploadDate
      const nickName = store.state.root.nickName
      const payload = {
        'teamId': teamId,
        'body': {
          'writer': writer,
          'teamName': teamName,
          'title': title,
          'content': content,
          'uploadDate': uploadDate,
          'nickName': nickName
        }
      }
      // console.log('게시글 작성')
      // console.log(payload)

      store.dispatch('root/addArticle', payload)
      .then(function (result) {
        console.log('게시글 등록 성공')
        // console.log(uploadDate.slice(0, -3))
        emit('createArticle')
        swal({
          title: "게시글 등록",
          text: "게시글이 등록되었습니다.",
          icon: "success",
          button: "확인",
        })
      })
      .catch(function (error) {
        console.log('게시글 등록 실패')
        console.log(error.response)
      })
      handleClose()
    }

    const updateArticle = function () {
      const writer = props.article.writer
      const teamName = props.article.teamName
      const title = props.article.title
      const content = props.article.content
      const uploadDate = props.article.uploadDate
      const nickName = props.article.nickName
      const id = props.article.id
      const payload = {
        'id' : id,
        'body': {
          'writer': writer,
          'teamName': teamName,
          'title': title,
          'content': content,
          'uploadDate': uploadDate,
          'nickName': nickName
        }
      }

      store.dispatch('root/updateArticle', payload)
      .then(function (result) {
        console.log('게시글 수정 완료')
        emit('updateArticle', uploadDate)
        swal({
          title: "게시글 수정",
          text: "게시글이 수정되었습니다.",
          icon: "success",
          button: "확인",
        })
      })
      .catch(function (error) {
        console.log('게시글 등록 실패')
      })

      handleClose()
    }

    return { state, handleClose, createArticleForm, createArticle, updateArticle }
  }
}
</script>

<style>
.create-schedule-dialog {
  width: 700px;
  height: 600px;
}
</style>
