<template>
  <!-- <p>{{ info }}</p> -->
  <el-dialog
    custom-class="birthday-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        생일 등록
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 150px;">
      <div style="border: 1px solid black; background-color: black; border-radius: 5px; font-size: 20px; padding: 5px 1rem;">
        생일을 입력해주세요.
      </div>
      <div class="d-flex justify-content-between">
        <div style="width: 100%" class="me-2">
          <el-date-picker
          style="width: 100%;"
          v-model="state.form.inputBirthday"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="Birthday"
          >
          </el-date-picker>
        </div>
        <div>
          <el-button @click="createBirthday">생일 등록</el-button>
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
  name: 'birthday-dialog',
  emits: ['create-birthday'],
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
        inputBirthday: null,
      }
    })

    const handleClose = function () {
      state.form.inputBirthday = ''
      emit('closeBirthdayDialog')
    }

    const createBirthday = function () {
      if (state.form.inputBirthday) {
        emit('create-birthday', {
          birthday: state.form.inputBirthday,
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
          'nickName': props.info.nickName,
          'birthDay': state.form.inputBirthday,
        }
        store.dispatch('root/editBirthday',{ 'body': body, 'token': token})
        // console.log(props.info.cdate)
        .then(res => {
          setTimeout(() => {
                swal({
                  title: "생일 등록",
                  text: "생일이 성공적으로 등록되었습니다..",
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

    return { state, handleClose, createBirthday}
  }

}
</script>

<style>
  .birthday-dialog {
    width: 700px;
    height: 300px;
  }
</style>
