<template>
  <div class="row">
    <div class="col-10">
      <el-calendar ref="calendar">
        <!-- 달력 헤더 부분 -->
        <template #header="{ date }">
          <div class="d-flex align-items-center">
            <div class="me-2">
              <el-avatar :size="70" v-if="state.team.teamPicture!=null" :src="`http://localhost:8080/api/v1/team/profileimg/${state.team.teamId}`"></el-avatar>
              <el-avatar :size="70" v-else :src="require('@/assets/images/agit_logo.png')"></el-avatar>
            </div>
            <div>
              <h3>{{ state.team.teamName }}</h3>
              <p>{{ state.team.teamDescription }}</p>
            </div>
            <div>
              <div v-if="state.team.confState" id="agit-on" class="d-flex justify-content-center align-items-center" @click="joinConference(state.team.teamId)">
              <el-tooltip
                class="box-item"
                effect="dark"
                content="아지트 참석"
                placement="top"
              >
                <div>
                  어서 드루와~
                </div>
              </el-tooltip>
              </div>
              <div v-else id="agit-off" class="d-flex justify-content-center align-items-center" @click="joinConference(state.team.teamId)">
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="아지트 참석"
                  placement="top"
                >
                <div>
                 아무도 없어요
                </div>
                </el-tooltip>
              </div>
            </div>
          </div>

          <div class="d-flex flex-column">
            <h3>{{ date }}</h3>
            <div>
              <span>
                <span class="badge-box-test">
                  <span class="badge-tag-test schedule me-2">게시글 수</span>
                  <span class="badge-tag-test picture me-2">사진 수</span>
                  <span class="badge-tag-test video">동영상 수</span>
                </span>
              </span>
            </div>
          </div>

          <div class="d-flex justify-content-between align-items-end">
              <el-button size="small" @click="selectDate('prev-year')"
                >&lt;&lt;</el-button
              >
              <el-button size="small" @click="selectDate('prev-month')"
                >&lt;</el-button
              >
              <el-button size="small" @click="selectDate('today')">Today</el-button>
              <el-button size="small" @click="selectDate('next-month')"
                >&gt;</el-button
              >
              <el-button size="small" @click="selectDate('next-year')"
                >&gt;&gt;</el-button
              >
            </div>
        </template>

        <!-- 달력 날짜 부분 -->
        <template #dateCell="{ data }" >
          <div class="d-flex flex-column justify-content-between date-on-calendar" style="height: 100%" @click="clickOnDate(data)">
            <div class="row">
              <!-- {{ data }} -->
              <span class="col-3 test">
                {{ data.day.split('-')[2] }}
              </span>
              <span class="col-3 button-on-calendar">
                <el-dropdown>
                  <span class="el-dropdown-link">
                    <i class="el-icon-plus"></i>
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item @click="onOpenCreateArticleDialog(data.day)">
                        <i class="fa-regular fa-pen-to-square"></i>
                        게시글 추가
                      </el-dropdown-item>
                      <!-- <el-dropdown-item @click="state.createArticleDialogOpen = true">
                        <i class="fa-regular fa-pen-to-square"></i>
                        게시글 추가
                      </el-dropdown-item> -->
                      <el-dropdown-item @click="onOpenCreateScheduleDialog(data.day)">
                        <i class="fa-regular fa-calendar-days"></i>
                        일정 추가
                      </el-dropdown-item>
                      <!-- <el-dropdown-item @click="state.createScheduleDialogOpen = true">
                        <i class="fa-regular fa-calendar-days"></i>
                        일정 추가
                      </el-dropdown-item> -->
                      <el-dropdown-item @click="onOpenUploadImageDialog(data.day)">
                        <i class="fa-regular fa-images"></i>
                        사진 추가
                      </el-dropdown-item>
                      <!-- <el-dropdown-item @click="state.uploadImageDialogOpen = true">
                        <i class="fa-regular fa-images"></i>
                        사진 추가
                      </el-dropdown-item> -->
                      <el-dropdown-item @click="onOpenUploadVideoDialog(data.day)">
                        <i class="fa-solid fa-video"></i>
                        동영상 추가
                      </el-dropdown-item>
                      <!-- <el-dropdown-item @click="state.uploadVideoDialogOpen = true">
                        <i class="fa-solid fa-video"></i>
                        동영상 추가
                      </el-dropdown-item> -->
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </span>
              <span class="col-9">
                <!-- <span v-bind="state.dict.articleCntDict"> -->
                <span v-if="data.day.toString() in state.dict.articleCntDict" class="badge-box-test">
                  <span class="badge-tag-test schedule">{{ state.dict.articleCntDict[data.day.toString()] }}</span>
                </span>
                <!-- </span> -->
                <span v-if="data.day.toString() in state.dict.imageCntDict" class="badge-box-test">
                  <span class="badge-tag-test picture">{{ state.dict.imageCntDict[data.day.toString()] }}</span>
                </span>
                <span v-if="data.day.toString() in state.dict.videoCntDict" class="badge-box-test">
                  <span class="badge-tag-test video">{{ state.dict.videoCntDict[data.day.toString()] }}</span>
                </span>
              </span>
            </div>
            <div class="badge-box">
              <!-- <div v-if="data.type === 'current-month'" class="badge-tag schedule">
                <div class="ms-3">
                  일정
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div> -->
              <div v-if="data.day.toString() in state.dict.eventDictEnd && data.type === 'current-month'">
                <div class="badge-tag article" v-for="(item, index) in state.dict.eventDictEnd[data.day.toString()]" :key="index">
                  <div class="ms-3">
                    {{ item.title }}
                  </div>
                  <div class="me-3">
                    <span class="badge" v-if="parseInt(item.dday) > 0">D-{{ item.dday }}</span>
                    <span class="badge" v-else-if="item.dday < 0">D+{{ -item.dday }}</span>
                    <span class="badge" v-else>D-day</span>
                  </div>
                </div>
              </div>
              <!-- <div v-if="data.day.toString() in state.dict.eventDictStart && data.type === 'current-month'">
                <div class="badge-tag article" v-for="(item, index) in state.dict.eventDictStart[data.day.toString()]" :key="index">
                  <div class="ms-3">
                    {{ item.title }}
                  </div>
                  <div class="me-3">
                    <span class="badge">Start</span>
                  </div>
                </div>
              </div> -->
              <!-- <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 10 && !(data.type === 'prev-month' || data.type === 'next-month')" class="badge-tag picture">
                <div class="ms-3">
                  사진
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div>
              <div v-if="parseInt(data.day.split('-').slice(2)[0], 10) <= 20 && !(data.type === 'prev-month' || data.type === 'next-month')" class="badge-tag video">
                <div class="ms-3">
                  동영상
                </div>
                <div class="me-3">
                  <span class="badge">4</span>
                </div>
              </div> -->
            </div>
          </div>
        </template>
      </el-calendar>
    </div>

    <!-- 오른쪽 멤버 화면 -->
    <room-member
    :teamId = "state.team.teamId"
    />
  </div>

  <!-- 일정 추가 다이얼로그 -->
  <create-schedule-dialog
    :open="state.createScheduleDialogOpen"
    :info="state.team"
    :registerDate="state.registerDate"
    @closeCreateScheduleDialog="onCloseCreateScheduleDialog"
    @createSchedule="onCreateEvent"/>

  <!-- 사진 추가 다이얼로그 -->
  <upload-image-dialog
    :open="state.uploadImageDialogOpen"
    :info="state.team.teamId"
    :registerDate="state.registerDate"
    @closeUploadImageDialog="onCloseUploadImageDialog"
    @createImage="onCreateEvent"/>

  <!-- 동영상 추가 다이얼로그 -->
  <upload-video-dialog
    :open="state.uploadVideoDialogOpen"
    :info="state.team.teamId"
    :registerDate="state.registerDate"
    @closeUploadVideoDialog="onCloseUploadVideoDialog"
    @createVideo="onCreateEvent"/>

  <!-- 게시글 추가 다이얼로그 -->
  <create-article-dialog
    :open="state.createArticleDialogOpen"
    :article="state.article"
    :isUpdated="state.isUpdated"
    :info="state.team"
    :registerDate="state.registerDate"
    @updateArticle="reloadBoardData"
    @closeCreateArticleDialog="onCloseCreateArticleDialog"
    @createArticle="onCreateEvent"/>

  <!-- 게시글 클릭 시 나오는 세부화면 -->
  <board
    :data="state.boardData"
    :open="state.boardOpen"
    @updateArticle="onUpdateArticle"
    @deleteArticle="onCreateEvent"
    @closeBoard="onCloseBoard"/>
  <!-- <el-drawer
    v-model="drawer"
    :title= "state.title"
    :direction="rtl"
  >
    <div style="border: 1px solid gray; height: 200px;">
      사진
      <div class="d-flex">
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
        <el-skeleton-item variant="image" style="width: 120px; height: 120px" class="me-3"/>
      </div>
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      동영상
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      TO-DO
    </div>
    <div style="border: 1px solid gray; height: 200px;">
      회의 메모
    </div>
  </el-drawer> -->
</template>

<script>

import { ref, reactive, computed, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

import CreateScheduleDialog from './components/create-schedule-dialog.vue'
import CreateArticleDialog from './components/create-article-dialog.vue'
import UploadImageDialog from './components/upload-image-dialog.vue'
import UploadVideoDialog from './components/upload-video-dialog.vue'
import Board from './components/board/board.vue'
import RoomMember from './components/roomMember.vue'


export default {
  name: 'room-board',
  components: {
    CreateScheduleDialog,
    CreateArticleDialog,
    UploadImageDialog,
    UploadVideoDialog,
    RoomMember,
    Board,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const calendar = ref()

    // const inviteDialogOpen = ref(false)
    // const createScheduleDialogOpen = ref(false)

    // 월 강제로 2자리 맞추기 (ex: 2 -> 02)
    function convertMonth (month) {
      if (month < 10) {
        return `0${month}`
      } else {
        return month.toString()
      }
    }

    // json -> dict
    function convertListToDict (list, dict) {
      for (let i = 0; i < list.length; i++) {
        let count = ''
        const length = list[i].count
        if (length >= 100) {
          count = '99+'
        } else {
          count = length.toString()
        }
        const uploadDate = list[i].uploadDate
        dict[uploadDate] = count
      }
    }

    function convertEventToDict (list, dict, key, value) {
      for (let i = 0; i < list.length; i++) {
        const dictKey = list[i][key.toString()]
        const dictValue = list[i][value.toString()]
        const title = list[i].eventTitle
        const dday = list[i].dday
        const content = {
          'value': dictValue,
          'title': title,
          'dday': dday
        }
        if (dictKey in dict) {
          dict[dictKey].push(content)
        } else{
          dict[dictKey] = [content]
        }
      }
    }

    // 팀 세부정보 가져오기
    function getTeamDetail () {
      let url = window.location.href;
      state.team.teamId = url.split('/').reverse()[0];

      store.dispatch('root/getTeamInfoDetail', state.team.teamId)
      .then(function(result){
        console.log('팀 세부정보 가져오기')
        console.log(result.data);
        state.team = result.data;
        // state.team.teamId = result.data.id;
        // console.log(state.team.teamId);
        // console.log(state.team.teamName)
      })
    }

    // 달력 개요(게시글, 사진, 동영상) 새로고침
    function reloadCalendar () {
      console.log('달력 개요 새로고침')

      store.dispatch('root/getTeamInfoDetail', state.team.teamId)
      .then(function(result){
        // console.log(result.data);
        state.team = result.data;
        state.team.teamId = result.data.id;

      })
      const payload = {
        'teamId': state.team.teamId,
        'uploadDate': state.team.uploadDate
      }
      store.dispatch('root/getCategoryCount', payload)
      .then(function (result) {
        convertListToDict(result.data.articleCntList, state.dict.articleCntDict)
        convertListToDict(result.data.imageCntList, state.dict.imageCntDict)
        convertListToDict(result.data.videoCntList, state.dict.videoCntDict)
        // console.log(state.dict.articleCntDict)
      })
      .catch(function (error) {
        console.log('해당 달의 개요 가져오기 실패')
      })
    }

    // 달력 개요(일정) 새로고침
    function reloadEvent () {
      const payload = {
        'teamId': state.team.teamId,
        'reqDate': state.team.uploadDate
      }

      store.dispatch('root/getEventCount', payload)
      .then(function (result) {
        console.log('일정 가져오는데 성공하였습니다.')
        state.dict.eventDictStart = {}
        state.dict.eventDictEnd = {}
        convertEventToDict(result.data, state.dict.eventDictStart, 'startDate', 'endDate')
        convertEventToDict(result.data, state.dict.eventDictEnd, 'endDate', 'startDate')
      })
      .catch(function (error) {
        console.log('일정 가져오는데 실패하였습니다.')
      })
    }


    const checkUserState = function(){
      // let url = window.location.href;
      // state.team.teamId = url.split('/').reverse()[0];

      if(state.isLogin==null){
        setTimeout(() => {
                swal({
                  title: "로그인 필요한 페이지",
                  text: "로그인 후 이용해주세요.",
                  icon: "success",
                  button: "확인",
                });
              }, 500)
       router.push({
        name: 'intro',
        })
      } else{
        console.log(state.isLogin);
        store.dispatch('root/checkTeamMember', {teamId: state.team.teamId, token:state.isLogin} )
        .then(function(result){
          console.log(result.data);
          console.log(result);
        })
        .catch(function(err){
          console.log(err.response)
          if(err.response.data.statusCode==1005){

            getTeamDetail();
            //비활성화 된 회원
            console.log(state.team.teamId);
            console.log(state.team.teamName);
            router.push({
              name: 'RoomConfirm',
              params: {
                roomId: state.team.teamId,
                roomName: state.team.teamName,
              },
            })
          }else if(err.response.data.statusCode==1002){
            //접근불가한 User
              router.push({
                name: 'Error'
              })
            }
          }
        )
      }
    }

    const state = reactive({
      team: {
        teamId: '',
        teamName: '',
        teamDescription: '',
        teamPicture: '',
        uploadDate: ''
      },
      dict: {
        articleCntDict: {},
        imageCntDict: {},
        videoCntDict: {},
        eventDictStart: {},
        eventDictEnd: {}
      },
      boardData: {
        teamId: '',
        articleList: [],
        eventResList: [],
        videoList: [],
        imageList: [],
        uploadDate: '1970-01-01'
      },
      article: {
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
      },
      isUpdated: false,
      registerDate: '1970-01-01',
      createScheduleDialogOpen: false,
      createArticleDialogOpen: false,
      uploadImageDialogOpen: false,
      uploadVideoDialogOpen: false,
      boardOpen: false,
      // year : '',
      // month : '',
      // day : '',
      token:'',
      // clickDate: '',
      title : computed(() => `${state.year}년 ${state.month}월 ${state.day}일 게시판`),
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      teamMembers: null,
      isLogin: computed(() => store.getters['root/getJWTToken'])
    })

    const clickOnDate = function (data) {
      if (data.type === 'current-month') {
        const teamId = state.team.teamId
        const uploadDate = data.day
        const payload = {
          'teamId': teamId,
          'uploadDate': uploadDate
        }
        store.dispatch('root/getBoardDetail', payload)
        .then(function (result) {
          console.log('게시판 상세조회 성공')
          console.log(result.data);
          state.boardData = result.data
          state.boardData['uploadDate'] = uploadDate
          state.boardOpen = true
        })
        .catch(function (error) {
          console.log('게시판 상세조회 실패')
        })
      } else {
        state.boardOpen = false
      }

    }

    const selectDate = (val) => {
      let url = window.location.href;
      state.team.teamId = url.split('/').reverse()[0];

      calendar.value.selectDate(val)
      const date = calendar.value.date
      const year = date.$y
      const month = convertMonth(date.$M + 1)
      state.team.uploadDate = `${year}-${month}`

      reloadCalendar()
      reloadEvent()
    }

    const onCloseInviteDialog = function () {
      state.inviteDialogOpen = false
    }

    const onOpenCreateScheduleDialog = function (val) {
      if (val in state.dict.eventDictEnd && state.dict.eventDictEnd[val].length >= 3) {
        swal({
          title: "약속이 너무 많아요...",
          text: "바쁘게 사는 것도 좋지만, 여유를 가져보는 것은 어떨까요?",
          icon: "warning",
          button: "쉬러가기",
        })
      } else {
        state.registerDate = val
        state.createScheduleDialogOpen = true
      }
    }

    const onCloseCreateScheduleDialog = function () {
      state.createScheduleDialogOpen = false
    }

    const onOpenCreateArticleDialog = function (val) {
      state.registerDate = val
      state.createArticleDialogOpen = true
    }

    const onCloseCreateArticleDialog = function () {
      state.createArticleDialogOpen = false
      state.isUpdated = false
      state.article = {
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

    const onUpdateArticle = function (data) {
      state.article = data
      state.createArticleDialogOpen = true
      state.isUpdated = true
    }

    const onCreateEvent = function () {
      const today = new Date()
      const year = today.getFullYear()
      const month = convertMonth(today.getMonth() + 1)
      state.team.uploadDate = `${year}-${month}`
      calendar.value.selectDate('today')
      reloadCalendar()
      reloadEvent()
    }

    const onOpenUploadImageDialog = function (val) {
      state.registerDate = val
      state.uploadImageDialogOpen = true
    }

    const onCloseUploadImageDialog = function () {
      state.uploadImageDialogOpen = false
    }

    const onOpenUploadVideoDialog = function (val) {
      state.registerDate = val
      state.uploadVideoDialogOpen = true
    }

    const onCloseUploadVideoDialog = function () {
      state.uploadVideoDialogOpen = false
    }

    const onCloseBoard = function () {
      state.boardOpen = false
    }

    const reloadBoardData = function (uploadDate) {
      const teamId = state.team.teamId
      const payload = {
          'teamId': teamId,
          'uploadDate': uploadDate
        }
      store.dispatch('root/getBoardDetail', payload)
      .then(function (result) {
        state.boardData = result.data
        state.boardData['uploadDate'] = uploadDate
      })
      .catch(function (error) {
        console.log('게시판 상세조회 실패')
      })

    }

    const joinConference = function (roomId) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: roomId
        }
      })
    }
    // const getTeamDetail = function(){
    //   store.dispatch('root/getTeamInfoDetail', state.team.teamId)
    //   .then(function(result){
    //     console.log(result.data);
    //     state.team = result.data;
    //     state.team.teamId = result.data.id;
    //     console.log(state.team.teamId);
    //     console.log(state.team.teamName)
    //   })
    // }

    // const checkUserState = function(){
    //   let url = window.location.href;
    //   state.team.teamId = url.split('/').reverse()[0];

    //   if(state.isLogin==null){
    //     setTimeout(() => {
    //             swal({
    //               title: "로그인 필요한 페이지",
    //               text: "로그인 후 이용해주세요.",
    //               icon: "success",
    //               button: "확인",
    //             });
    //           }, 500)
    //    router.push({
    //     name: 'intro',
    //     })
    //   }
    //   else{
    //     console.log(state.isLogin);
    //     store.dispatch('root/checkTeamMember', {teamId: state.team.teamId, token:state.isLogin} )
    //   .then(function(result){
    //     console.log(result.data);
    //     console.log(result);
    //   }).catch(function(err){
    //     console.log(err.response)
    //     if(err.response.data.statusCode==1005){

    //       getTeamDetail();
    //       //비활성화 된 회원
    //       console.log(state.team.teamId);
    //       console.log(state.team.teamName);
    //       router.push({
    //       name: 'RoomConfirm',
    //       params: {
    //         roomId: state.team.teamId,
    //         roomName: state.team.teamName,
    //       },
    //     })
    //     }else if(err.response.data.statusCode==1002){
    //       //접근불가한 User
    //         router.push({
    //           name: 'Error'
    //         })

    //       }
    //     })
    //   }
    // }

    //   const takeMember = function () {
    //     const token = store.getters['root/getJWTToken']
    //     const body = {
    //       'teamId': state.team.teamId
    //     }
    //     store.dispatch('root/takeMember', {'body': body, 'token': token})
    //     .then(res=> {
    //       console.log(res.data)
    //       state.teamMembers = res.data
    //       console.log(state.teamMembers[0].name)
    //     })
    //     .catch(err => {
    //       console.log(err)
    //       // console.log('기달')
    //     })
    //   }

    onBeforeMount(() => {
      let url = window.location.href;
      state.team.teamId = url.split('/').reverse()[0];
      const today = new Date()
      const year = today.getFullYear()
      const month = convertMonth(today.getMonth() + 1)
      state.team.uploadDate = `${year}-${month}`

      checkUserState();
      getTeamDetail();
      reloadCalendar()
      reloadEvent()

    })

    return { clickOnDate, state, selectDate, calendar, onCloseInviteDialog, onCloseCreateScheduleDialog, onCloseUploadImageDialog, onCloseUploadVideoDialog, onCloseCreateArticleDialog, onCloseBoard, onCreateEvent,
            onOpenCreateArticleDialog, onUpdateArticle, reloadBoardData, onOpenCreateScheduleDialog, onOpenUploadImageDialog, onOpenUploadVideoDialog, joinConference }
  }

}
</script>

<style>


/* .el-calender .el-calendar-table {
  tran
} */

/* 달력 바닥이 다 보이도록 마진 설정 */
.el-calendar .el-calendar-table {
  margin-bottom: 150px;
}

/* .el-calendar .el-calendar__header {
  pointer-events: none;
} */

/* 달력 날짜 한 칸이 너무 작아서 높이 설정 */
.el-calendar .el-calendar-table > tbody > tr > td .el-calendar-day{
  height: 200px;
}

.el-calendar .el-calendar__body .el-calendar-table thead th {
  text-align: center;
}

.el-calendar-table thead th:first-child,
.el-calendar-table__row td:first-child {
  color: #ff6629;
}

.el-calendar-table thead th:last-child,
.el-calendar-table__row td:last-child {
  color: #4e4ebb;
}

.el-calendar-table tr:first-child td:first-child {
  border-top: 3px solid #ff6629;
}

.el-calendar-table tr:first-child td:last-child {
  border-top: 3px solid #4e4ebb;
}

.el-calendar-table tr:first-child td {
  border-top: 3px solid #606266;
}

.el-calendar-table:not(.is-range) td.next,
.el-calendar-table:not(.is-range) td.prev {
  color: #c0c4cc;
}

.el-calendar-table tr td {
  border-left: none;
  border-right: none;
  border-bottom: 2px solid #606266;
}

.el-calendar-table tr td:first-child {
  border-left: none;
  border-right: none;
  border-bottom: 2px solid #ff6629;
}

.el-calendar-table tr td:last-child {
  border-left: none;
  border-right: none;
  border-bottom: 2px solid #4e4ebb;
}

/*
.el-calendar-table__row td:first-child {
  color: red;
} */

/* 게시판 내에 존재하는 일정, 사진, 동영상 등의 뱃지 css */
.badge-tag {
  font-size: 11px;
  border-radius: 5px;
  color: white;
  font-weight: bolder;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
  padding: 0;
}
.badge-tag-test {
  font-size: 0.8rem;
  border-radius: 10px;
  color: white;
  font-weight: bolder;
  /* display: flex; */
  /* justify-content: space-between; */
  /* align-items: center; */
  /* margin-bottom: 0.5rem; */
  /* padding-left: 2px;
  padding-right: 2px;
  padding-top: 2px; */
  padding: 0px 7px;
  /* margin-right: 50px; */
}

.badge-tag .badge {
  margin: 0;
  text-align: center;
  background-color: grey;
}

.badge-box {
  width: 90%;
  margin: 0 auto;
  line-height: 150%;
}
.badge-box-test {
  width: 90%;
  margin: 0 auto;
  line-height: 150%;
  margin-right: 5px;
}

.badge-box .schedule {
  border: 1px solid #ff6c7fd5;
  background-color: #ff6c7fd5;
}

.badge-box .picture {
  border: 1px solid #6c82ffd5;
  background-color: #6c82ffd5;
}

.badge-box .video {
  border: 1px solid #6b6b6bd5;
  background-color: #6b6b6bd5;
}
.badge-box-test .schedule {
  border: 1px solid #ff6c7fd5;
  background-color: #ff6c7fd5;
}

.badge-box-test .picture {
  border: 1px solid #6c82ffd5;
  background-color: #6c82ffd5;
}

.badge-box-test .video {
  border: 1px solid #6b6b6bd5;
  background-color: #6b6b6bd5;
}

.badge-box .article {
  border: 1px solid #6ac7b3d5;
  background-color: #6ac7b3d5;
}

.button-on-calendar {
  display: none;
}

.date-on-calendar:hover .button-on-calendar {
  display: inline;
}

.date-on-calendar:hover .test {
  display: none;
}

.el-dropdown {
  border: 1px solid #bfc3c8;
  border-radius: 5px;
}

.el-dropdown:hover {
  background-color: #cbced4;
  color: #ffffff;
}
#agit-on{
  border-style: solid;
  border-radius: 20px;
  font-size: 12px;
  width: 80px;
  height: 30px;
  background-color: #f56c6c;
  margin-left: 1.5vh;
  cursor: pointer;
}
#agit-off{
  border-style: solid;
  border-radius: 20px;
  font-size: 12px;
  width: 80px;
  height: 30px;
  background-color: #717275;
  margin-left: 1.5vh;
  cursor: pointer;
}
</style>
