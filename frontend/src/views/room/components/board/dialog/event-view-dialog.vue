<template>
  <!-- <p>{{ info }}</p> -->
  <el-dialog
    custom-class="event-view-dialog"
    v-model="open"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        일정 상세보기
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around">
      <h1 style="text-align: center; margin-bottom: 5rem;">{{ info.eventTitle }}</h1>
      <p style="font-size: 18px;">
        기간 : {{ info.endDate }} ({{ state.dDay }})
      </p>
      <!-- <div class="d-flex justify-content-between">
        <div>
          <p style="font-size: 1.1rem;">
            {{ info.writer }}
          </p>
        </div>
        <div style="font-size: 40%;">
          <p>
            언제부터? {{ info.startDate }}
          </p>
          <p>
            언제까지? {{ info.endDate }}
          </p>
        </div>
      </div> -->

      <div style="border-top: 1px solid black; padding-top: 1.5rem;">
        <p>
          내용
        </p>
        <p style="font-size: 25px;">
          {{ info.eventContent }}
        </p>
      </div>
    </div>
    <!-- footer -->
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import jwt_decode from 'jwt-decode'

export default {
  name: 'event-view-dialog',
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
      userId: computed(() => jwt_decode(store.getters['root/getJWTToken']).sub),
      dDay: computed(function () {
        const dday = props.info.dday
        console.log('디데이')
        console.log(dday)
        let result = ''
        if (dday < 0) {
          result = `D + ${-dday}`
        } else if (dday > 0) {
          result = `D - ${dday}`
        } else {
          result = `D - day`
        }

        return result
      })
    })

    const handleClose = function () {
      emit('closeEventView')
    }

    const timeFormat = function (time) {
      const now = new Date().getTime()
      const createdAt = new Date(time)

      const timeDiff = Math.floor((now - createdAt.getTime()) / 1000 / 60)
      const dayDiff = Math.floor(timeDiff / 60)
      if (timeDiff <= 5) return '방금전'
      else if (timeDiff < 60) return `${timeDiff}분 전`
      else if (dayDiff < 24) return `${dayDiff}시간 전`
      else {
        const year = createdAt.getFullYear()
        const month = createdAt.getMonth() + 1
        const day = createdAt.getDate()
        const hour = createdAt.getHours()
        const minute = createdAt.getMinutes()
        return `${year}.${month}.${day} ${hour}:${minute}`
      }
    }

    return { state, handleClose, timeFormat }
  }

}
</script>

<style>
.event-view-dialog {
  width: 700px;
  height: 550px;
}

.event-view-dialog .el-overlay-dialog {
  background-color: rgba(0,0,0,.5) !important;
}

.event-view-dialog .el-dialog,
.event-view-dialog .el-dialog__body,
.event-view-dialog .el-dialog__body div {
  background-color: white !important;
}

.event-view-dialog .el-dialog__body {
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
}

/* .el-dialog__footer {
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  background-color: white !important;
}

.el-dialog__footer > div {
  background-color: white !important;
} */

</style>
