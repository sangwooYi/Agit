<template>
  <el-dialog
    custom-class="create-schedule-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
    :destroy-on-close="true"
  >
    <!-- header -->
    <template #title>
      <span>
        일 정 추 가
      </span>
    </template>

    <!-- content -->
    <div>
      <div>
        일정을 추가해보세요.
      </div>
      <div>
        <el-form :model="state.form" :rules="state.rules" ref="createScheduleForm" :label-position="state.form.align">
          <!-- <el-form-item prop="startdate" label="시작일자">
            <el-date-picker
              style="width: 100%;"
              v-model="registerDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
            >
            </el-date-picker>
          </el-form-item> -->
          <el-form-item prop="enddate" label="날짜">
            <el-date-picker
              style="width: 100%;"
              v-model="state.form.enddate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="일자를 선택해주세요."
              disabled
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item prop="title" label="제목">
            <el-input
              v-model="state.form.title"
              maxlength="12"
              placeholder="약속의 제목을 입력해주세요."
              show-word-limit
              rows="1"
              type="textarea"
              resize="none"
            >
            </el-input>
          </el-form-item>
          <el-form-item prop="content" label="내용">
            <el-input
              v-model="state.form.content"
              maxlength="100"
              placeholder="약속의 내용을 입력해주세요."
              show-word-limit
              rows="5"
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
      <span>
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="createSchedule">약속잡기</el-button>
      </span>
      <!-- <span>
        {{ info.teamId }}
      </span> -->
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'create-schedule-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    },
    registerDate: {
      type: String,
      default: '1970-01-01'
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const createScheduleForm = ref(null)

    const state = reactive({
      form: {
        align: 'left',
        content: '',
        startdate: computed(() => props.registerDate),
        enddate: computed(() => props.registerDate),
        title: '',
        teamId: '',
      },
      rules: {
        startdate: [
          { required: true, message: '날짜 선택은 필수입니다.', trigger: 'blur'}
        ],
        enddate: [
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
      state.form.content = ''
      state.form.title = ''
      // state.form.enddate = ''
      emit('closeCreateScheduleDialog')
    }

    const createSchedule = function () {
      const token = store.getters['root/getJWTToken']
      const body = {
        // 'teamId': props.info.teamId,
        'eventTitle': state.form.title,
        'eventContent': state.form.content,
        'startDate': state.form.startdate,
        'endDate': state.form.enddate
      }
      const teamId = props.info.teamId
      const uploadDate = state.form.startdate.slice(0, -3)
      // const teamId = props.info.teamId
      store.dispatch('root/createSchedule',{ 'body': body, 'token': token, 'teamId': teamId})
      .then(res => {
        setTimeout(() => {
          swal({
            title: '약속 등록',
            text: '약속이 일정에 등록되었습니다.',
            icon: 'success',
            button: '확인',
          });
        }, 500)

        // router.go(router.currentRoute)
        console.log(res)
        // console.log(swal)
        emit('createSchedule')
        handleClose()
      })
      .catch(err => {
        console.log(err)
      })

      handleClose()
    }



    return { state, handleClose, createScheduleForm, createSchedule, store, router }
  }
}
</script>

<style>
.create-schedule-dialog {
  width: 700px;
  height: 450px !important;
}


</style>
