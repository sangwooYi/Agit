<template>
  <div v-if="info">
    <el-dialog custom-class="after-dialog" v-model="state.dialogVisible" title="다음 약속" width="30%">
      <div class="card text-dark" style="margin-bottom: -20px;">
        <div v-if="teamP">
          <img :src=teamP class="card-img" onerror="src='https://cdn.pixabay.com/photo/2017/04/10/22/28/residence-2219972_960_720.jpg'" alt="https://i6a403.p.ssafy.io/img/agit_logo.e339fd5c.png"  style="opacity: 0.3; max-height: 500px;">
          <div class="card-img-overlay" style="color: #000000;">
            <h3 class="card-title">팀명: {{ info.teamName }}</h3>
            <br>
            <h5 class="card-text">약속 제목: {{ info.eventTitle }}</h5>
            <h5 class="card-text">약속 내용: {{ info.eventContent }}</h5>
            <br>
            <p class="card-text">약속 날짜: {{ info.endDate }}</p>
          </div>
        </div>
        <!-- <div v-else-if="store.state.root.props.info.teamId.teamPicture==null">
          <img src='https://i6a403.p.ssafy.io/img/agit_logo.e339fd5c.png' class="card-img" alt="..." style=" opacity: 0.5;">
          <div class="card-img-overlay" style="color: #464646">
            <h3 class="card-title">팀명: {{ info.teamName }}</h3>
            <br>
            <h5 class="card-text">약속 제목: {{ info.eventTitle }}</h5>
            <h5 class="card-text">약속 내용: {{ info.eventContent }}</h5>
            <br>
            <p class="card-text">시작 날짜: {{ info.startDate }}</p>
            <p class="card-text">종료 날짜: {{ info.endDate }}</p>
          </div>
        </div> -->
        </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleClose()"
            >Confirm</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
  <div v-else-if="info == undefined">
    <el-dialog custom-class="after-dialog" v-model="state.dialogVisible" title="다음 약속" width="30%" style="margin: 50%">
      <p>
      다음 일정이 없습니다.
      </p>
      <br>
      <p>팀 생성 후 일정을 추가할 수 있습니다 <i class="em em-closed_book" aria-role="presentation" aria-label="CLOSED BOOK" style="margin-bottom:4px"></i></p>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleClose()"
            >Confirm</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'


export default {
  name: 'after-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      required: true,
    },
    teamP: {
      type: String,
    }
  },
  setup(props, {emit}) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
      teamP : computed(()=>props.teamP),
      // teamPicture : 'http://localhost:8080/api/v1/team/profileimg/'+ props.info.teamId,
    })

    // console.log(props.teamP)
    // console.log('애프터에서')










    // takeTeam()

  // console.log(state.team.teamPicture)



  // console.log(state.team.teamPicture)
  // console.log('여기일듯')


    const handleClose = function () {
      emit('closeAfterDialog')
    }

    return { state, store, router, handleClose}
  }
}
</script>

<style>

</style>
