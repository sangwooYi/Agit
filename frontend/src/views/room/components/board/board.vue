<template>
  <div class="board-container">
    <el-drawer
      v-model="state.boardVisible"
      :direction="rtl"
      :before-close="handleClose"
      :lock-scroll="true"
    >
      <template #title>
        <!-- <div width="50%">
          <img :src="require(`@/assets/images/agit_logo.png`)">
        </div> -->
        <div class="d-flex align-items-center">
          <el-image fit="scale-down" :src="require(`@/assets/images/agit_logo.png`)" style="height: 50px; width: 50px;"></el-image>
          <span class="ms-3">
            {{ state.title }}
          </span>
        </div>
      </template>
      <el-scrollbar max-height="90vh">

        <div class="mb-4" style="width: 95%; margin-left: auto; margin-right: auto;">
          <h4>
            <i class="em em-spiral_calendar_pad" aria-role="presentation" aria-label=""/>
            오늘의 일정
          </h4>
          <div v-if="state.eventResList.length >= 1">
            <event-page
              :eventList = state.eventResList
              :uploadDate = data.uploadDate
            />
          </div>
          <div v-else>
            <el-empty :image-size="200" description="약속이 없어요.">
              <template #image>
                <img :src="require(`@/assets/images/schedule-empty.png`)">
              </template>
            </el-empty>
          </div>
        </div>
        <div class="mb-4" style="width: 95%; margin-left: auto; margin-right: auto;">
          <h4>
            <i class="em em-camera_with_flash" aria-role="presentation" aria-label="CAMERA WITH FLASH"/>
            사진
          </h4>
          <div v-if="state.imageList.length >= 1">
            <image-page
            :uploaddate = state.uploadDate
            :teamId = state.teamId
            :srcList = state.imageList></image-page>
          </div>
          <div v-else>
            <el-empty :image-size="60" description="사진을 등록해주세요."/>
          </div>
        </div>
        <div class="mb-4" style="width: 95%; margin-left: auto; margin-right: auto;">
          <h4>
            <i class="em em-movie_camera" aria-role="presentation" aria-label="MOVIE CAMERA"></i>
            비디오
          </h4>
          <div v-if="state.videoList.length >= 1">
            <video-page
            :uploaddate = state.uploadDate
            :teamId = state.teamId
            :srcList = state.videoList></video-page>
          </div>
          <div v-else>
            <el-empty :image-size="60" description="비디오를 등록해주세요."/>
          </div>
        </div>
        <div class="mb-4" style="width: 95%; margin-left: auto; margin-right: auto;">
          <h4>
            <i class="em em-clipboard" aria-role="presentation" aria-label="CLIPBOARD"></i>
            게시판
          </h4>
          <div style="margin: 20px 10px 50px 10px;">
            <article-page
              :articleList = state.articleList
              @updateArticle="onUpdateArticle"
              @deleteArticle="onDeleteArticle"
            />
            <!-- <el-table :data="state.articleList" height="40vh" style="width: 100%">
              <el-table-column prop="index" label="글번호" width="80" />
              <el-table-column prop="title" label="글제목" width="180" />
              <el-table-column prop="content" label="글내용" />
            </el-table> -->
          </div>
        </div>
      </el-scrollbar>
    </el-drawer>
  </div>
</template>

<script>
import { reactive, computed, onBeforeMount } from 'vue'
import ImagePage from './image.vue'
import VideoPage from './video.vue'
import ArticlePage from './article.vue'
import EventPage from './event.vue'

export default {
  name: 'board',
  components:{
    ImagePage,
    VideoPage,
    ArticlePage,
    EventPage,
  },
  props: {
    data: {
      type: Object,
    },
    open: {
      type: Boolean,
      default: false,
    },

  },

  setup(props, { emit }) {
    const state = reactive({
      boardVisible: computed(() => props.open),
      // teamId: props.teamId,
      // articleList: props.data.articleList,
      // imageList: props.data.imageList,
      // videoList: props.data.videoList,
      // eventResList: props.data.eventResList,
      uploadDate: computed(()=>props.data.uploadDate),
      teamId: computed(() => props.data.teamId),
      articleList: computed(function () {
        let articleList = props.data.articleList
        for (let i = 0; i < articleList.length; i++) {
          articleList[i]['index'] = i + 1
        }
        return articleList
      } ),

      imageList: computed(function(){
        let imageList = props.data.imageList
        for ( let i=0; i<imageList.length; i++){
          imageList[i] = 'http://localhost:8080/api/v1/image/'+imageList[i].id;
        }
        console.log(imageList)
        return imageList;
      }),
      videoList: computed(function(){
        let videoList = props.data.videoList
        for ( let i=0; i<videoList.length; i++){
          videoList[i] = 'http://localhost:8080/api/v1/video/'+videoList[i].id;
        }
        console.log(videoList);
        return videoList;
      }),
      eventResList: computed(() => props.data.eventResList),
      YMD: computed(() => props.data.uploadDate.split('-')),
      title: computed(() => `${state.YMD[0]}년 ${state.YMD[1]}월 ${state.YMD[2]}일 게시판`)
    })

    console.log(state.teamId);
    const handleClose = function () {
      emit('closeBoard')
    }

    const onDeleteArticle = function () {
      emit('deleteArticle')
    }

    const onUpdateArticle = function (data) {
      emit('updateArticle', data)
    }

    return { state, handleClose, onDeleteArticle, onUpdateArticle }
  }
}
</script>

<style>
.board-container div {
  background-color: #d2f5ed;
}
.scrollbar-flex-content {
  display: flex;
}


.el-drawer {
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
}

h4 {
  overflow: hidden;
  text-align: center;
  margin-bottom: 2rem;
}

h4:before,
h4:after {
  background-color: #000;
  content: "";
  display: inline-block;
  height: 1px;
  position: relative;
  vertical-align: middle;
  width: 50%;
}

h4:before {
  right: 0.5em;
  margin-left: -50%;
}

h4:after {
  left: 0.5em;
  margin-right: -50%;
}

.el-overlay {
  animation: unfoldIn 0.25s cubic-bezier(.165,.84,.44,1) forwards;
  -webkit-animation: unfoldIn 0.25s cubic-bezier(.165,.84,.44,1) forwards;
}

.el-table {
  border-radius: 10px;
  border: 1px solid black;
}

.el-table tr th .cell{
  text-align: center;
}

.el-table .cell {
  background-color: transparent;
}

.el-table .el-table__body tr:hover {
  transition: 0.1s;
  font-size: 20px;
  /* background-color: black !important; */
}

</style>
