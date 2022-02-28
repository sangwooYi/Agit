<template>

  <div id="chat-container"  v-bind:style="{ 'background-image': 'url(' + state.backImg + ')', 'background-repeat': 'repeat' }">
    <div v-if="!state.session">
      <div class="d-flex flex-column justify-content-center align-items-center join-room">
        <h1>아지트 참석하기</h1>
        <h3 class="my-3">현재 접속하려는 방: {{ state.teamName }}의 방</h3>
        <div>
          <el-button type="primary" @click="joinSession" id="join-btn" class="mx-3">
            <i class="fa-solid fa-right-to-bracket"></i>
          </el-button>
          <el-button type="danger" @click="outSession" id="join-btn">
            <i class="fa-solid fa-xmark"></i>
          </el-button>

        </div>
      </div>
    </div>
    <div class="d-flex-row justify-content-between my-3" v-if="state.session">
        <div class="d-flex justify-content-between align-items-center" id="header" data-html2canvas-ignore="true">
          <div>
          </div>
          <h1 id="conference-name" class="text-center">{{ state.teamName }}'s Room</h1>
            <!-- <div class="logo" id="neon" style="width: 100%; height: 35vh;">
              <b><span>a</span><span>g</span>i<span>t</span></b>
            </div> -->
          <h2 id="close-btn" class="d-flex justify-content-center align-items-center" @click="closeSession">X</h2>
        </div>
      <div class="d-flex justify-content-between">
        <!-- <div id="main-video" class="col-3 mx-3">
              <user-video :stream-manager="state.mainStreamManager"/>
            </div> -->
        <div class="d-flex flex-wrap mx-3">
          <user-video :stream-manager="state.publisher" :border-color="state.BorderColor"/>
          <user-video
            v-for="sub in state.subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            :border-color="sub.element"
          />
        </div>
        <div id="rec-test">
        </div>
        <div>
          <chat-live :session="state.session" @sendMessage="sendMessage" />
        </div>
      </div>
      <div
      :height="`80px`"
      style="position:fixed; height:10%; bottom: 0; width: 100%; background-color: #2f3136; opacity:0.8" data-html2canvas-ignore="true">
      <div class="d-flex justify-content-center align-items-center" id="btn-group" style="height:100% ">
          <!-- 비디오 토글 버튼 -->
            <div>
              <div v-if="state.publisher.stream.videoActive">
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="카메라 OFF"
                  placement="top"
                >

                <i class="fa-solid fa-video-slash custom-icon toggle-icon-off text-center" @click="changeVideoState"></i>
                </el-tooltip>
              </div>
              <div v-else>
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="카메라 ON"
                  placement="top"
                >
                <i class="fa-solid fa-video custom-icon toggle-icon-on text-center"  @click="changeVideoState"></i>
                </el-tooltip>
              </div>
            </div>
            <!-- 오디오 토글 버튼 -->
            <div>
              <div v-if="state.publisher.stream.audioActive">
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="마이크 OFF"
                  placement="top"
                >
              <i class="fa-solid fa-microphone-slash custom-icon toggle-icon-off text-center" style="padding-left: 0.8vh" @click="changeAudioState"></i>
                </el-tooltip>
              </div>
              <div v-else>
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="마이크 ON"
                  placement="top"
                >
                <i class="fa-solid fa-microphone custom-icon toggle-icon-on text-center" @click="changeAudioState"></i>
                </el-tooltip>
              </div>
            </div>
            <!--녹화 하기 버튼-->
            <div>
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="레코딩"
                  placement="top"
                >
                <i class="fa-solid fa-film custom-icon share-icon text-center" @click="startRecording" id="start"></i>
                </el-tooltip>
            </div>
            <!--화면변경 버튼-->
            <div>
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="배경 이미지 변경"
                  placement="top"
                >
                <i class="fa-solid fa-image custom-icon share-icon text-center" @click="onOpenBackImgDialog"></i>

                </el-tooltip>
            </div>
            <!--사진캡쳐 버튼-->
            <div>
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="화면 캡쳐"
                  placement="top"
                >
              <i class="fa-solid fa-camera custom-icon share-icon text-center" id="capture" @click="takeSnapshot"></i>
                </el-tooltip>
            </div>
            <!--그림그리기 버튼-->
            <!-- <div>
                <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="그림그리기"
                  placement="top"
                >
              <i class="fa-solid fa-pen custom-icon share-icon text-center"  @click="screenshot"></i>
                </el-tooltip>
            </div> -->
        </div>
      </div>
      <select-back-img-dialog :open="state.backImgDialogOpen"
      @closeBackImgDialog="onCloseBackImgDialog"
      @backImg="setBackImg"/>
      <capture-img
      :open="state.captureImgDialogOpen"
      :captureImg="state.captureImg"
      :roomId="state.roomId"
      @closeCaptureImgDialog="onCloseCaptureImgDialog"/>
    </div>

  </div>
</template>
<style>
#conference-name {
  text-align: center;
}
.join-room{
  margin-top: 20vh;
  margin-left: 29vw;
  border-color: black;
  border-style: solid;
  border-radius: 15px;
  width: 100vh;
  height: 40vh;
  background-color: rgb(87, 193, 129);
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}

#join-btn {
  width: 8vw;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.25);
}

#join-btn i{
  transform: scale(1.2);
}

#video-btn {
  width: 10vh;
  height: 7vh;
}
#chat-container {
  position: absolute;
  top: -90px;
  left: -90px;
  margin-top: 0;
  z-index: 10;
  background-color: #36393f;
  width: 111.1%;
  height: 100vh;
  background-size:cover;
}
#close-btn {
  font-size: 45px;
  width: 80px;
  height: 65px;
  background-color: #b53638;
  border-style: solid;
  border-color: black;
  border-radius: 25px;
  border-width: 5px;
  cursor: pointer;
  margin-right: 2vw;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
  margin-right: 2vw;
}
#close-btn:hover{
  background-color: #c44749;
}
.custom-icon{
  margin-right: 3.2vw;
  transform: scale(2);
  border-color: black;
  border-style: solid;
  border-width: 0.3vh;
  padding: 0.8vh;
  width: 2.5vw;
  border-radius: 100px;
  cursor: pointer;
}
.toggle-icon-off{
  background-color: #b53638;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}
.toggle-icon-off:hover{
  background-color: #c44749;
}
.toggle-icon-on{
  background-color: #3d48c2;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
}
.toggle-icon-on:hover{
  background-color: #4753d3;
}
.share-icon{
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
  background-color: rgb(27, 26, 26);
  color: #f6f6f6;
}
.share-icon:hover{
  background-color: rgb(44, 43, 43);
}
#header{
  width: 100vw;
  margin-bottom: 2vh;
}
#header h1{
  position: relative;
  border-color: black;
  border-style: solid;
  padding: 1vh;
  border-radius: 30px;
  background-color: rgb(85, 174, 121);
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
  width: 60vh;
}
#conf-img{
  z-index: 9000;
}

/* 로고 */
.logo {
  text-align: center;
  width:100%;
  height: 15vh;
  margin: auto;
  position: relative;
  /* margin-top: 0px; */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
   user-select: none;
}

.logo b{
  font: 50 13vh "Vibur";
  color: #fee;
  text-shadow: 0 -40px 100px, 0 0 2px, 0 0 1em #FFEB5A, 0 0 0.5em #FFEB5A, 0 0 0.1em #FFEB5A, 0 10px 3px #000;
}
.logo b span{
  animation: blink linear infinite 2s;
  font: 50  13vh "Vibur";
}
.logo b span:nth-of-type(2){
  animation: blink linear infinite 3s;
  font: 50 13vh "Vibur";
}

#screenshot_background{
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0px;
  left: 0px;
  display: block;
  opacity: 0.3;
  text-align: center;
  box-sizing: border-box;
  z-index: 99999;
  border-color: black;
  border-style: solid;
}

#screenshot::before, #screenshot::after{
  border: none !important;
  content: "" !important;
  height: 100% !important;
  position : absolute !important;
  width: 100% !important;
}

#screenshot::before{
  border-right: 1px solid white !important;
  border-bottom: 1px solid white !important;
  left: -100% !important;
  top: -100% !important;
}

#screenshot::after{
  border-top: 1px solid white !important;
  border-left: 1px solid white !important;
  left: 0 !important;
  top: 0 !important;
}

#screenshot{
  position: fixed !important;
  width: 100% !important;
  height: 100% !important;
  z-index: 99999 !important;
}

body.edit_cursor{
  cursor: crosshair;
}

#record-lens{
  transform: translate(100%);
  z-index: 2000;
  position: absolute;
  color: #b53638;
}
</style>

<script>
import { reactive, onMounted, onUnmounted, computed, ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/live/UserVideo.vue';
import ChatLive from '@/views/live/ChatLive.vue';
import CaptureImg from '@/views/live/CauptureImgTest.vue'
import SelectBackImgDialog from '@/views/live/SelectBackImgDialog.vue';
import axios from 'axios';
import moment from 'moment';
import html2canvas from 'html2canvas'

axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
  name: 'conference-detail',

  components: {
    UserVideo,
    ChatLive,
    SelectBackImgDialog,
    CaptureImg
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const OPENVIDU_SERVER_URL = "https://i6a403.p.ssafy.io:5443";
    const OPENVIDU_SERVER_SECRET = "MY_SECRET";
    const instance = axios.create({
      baseURL: OPENVIDU_SERVER_URL,
      headers: {
        "Content-Type": "application/json",
      },
    });
    // screen record
    let startRec = document.getElementById('start')
    let stopRec = document.getElementById('stop')
    let mediaRecorder

    const state = reactive({
      conferenceId: '',
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: "SessionA",
      myUserName: "Person1",
      roomId: computed(() => route.params.conferenceId),
      teamName: '',
      userName: '',
      videoStatus: true,
      audioStatus: true,
      BorderColor: 'black',
      isLogin: computed(() => store.getters['root/getJWTToken']),
      backImgDialogOpen: false,
      captureImgDialogOpen: false,
      backImg:require('@/assets/images/defaultbackimg.jpg'),
      captureImg:'',
      recordState: true,
    });
    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId;
      store.commit("root/setMenuActiveMenuName", "home");
      checkUserState()
    });

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = "";
    });

    // 세션 Exit 버튼 눌렀을 때
    const closeSession = function () {
      // publisher가 본인 ////
      // 나머지가 subscirbers == 0//
      if (state.subscribers.length === 0){
        changeConfStateFalse()
      }
      leaveSession();
      router.push({
        name: "room-board",
        params: {
          roomId: state.roomId,
        },
      });
    };
    // 세션 진입 전 Exit할 때
    const outSession = function (){
        router.push({
        name: "room-board",
        params: {
          roomId: state.roomId,
        },
      });
    }

    const checkUserState = function(){
      if(state.isLogin==null){
        setTimeout(() => {
                swal({
                  title: '로그인 필요한 페이지',
                  text: '로그인 후 이용해주세요.',
                  icon: 'success',
                  button: '확인',
                });
              }, 500)
       router.push({
        name: 'intro',
        })
      }}

    const joinSession = function () {
      // 팀정보 0 = > 1로 바꾸어줌
      // vuex store에서 해당 팀에 상태 toggle (본인이 팀 추가할때마다 vuex store에 추가)
      changeConfStateTrue()
      state.OV = new OpenVidu();
      state.session = state.OV.initSession();
      state.session.on("streamCreated", ({ stream }) => {
        console.log(stream);
        const subscriber = state.session.subscribe(stream);
        subscriber.element = "black"
        state.subscribers.push(subscriber);
        // console.log("===============================")
        // console.log(state.subscribers)
      });

      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });
      state.myUserName = state.userName;
      state.mySessionId = state.roomId;

      state.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 채팅
      state.session.on("signal:chat", (event) => {
        let eventData = JSON.parse(event.data);
        let data = new Object();
        let time = new Date();
        data.message = eventData.content;
        if (state.currentMode === "anonymous") {
          data.sender = eventData.secretName;
        } else {
          data.sender = event.from.data.slice(15, -2);
        }
        data.time = moment(time).format("HH:mm");
        store.commit("root/setMessages", data);
      });

      // 음성 인지
      state.session.on('publisherStartSpeaking', (event) => {
          const publisherId = state.publisher.stream.streamId
          // console.log(publisherId)
          // publisher의 event면 publisher만 변경
          const str_len = event.connection.connectionId.length
          if (publisherId.slice(-str_len) === event.connection.connectionId){
            state.BorderColor = "#404eed"
          }
          state.subscribers.forEach((subscriber) => {

            // subscirber의 event면 subscriber 변경
            if (subscriber.stream.streamId.slice(-str_len) === event.connection.connectionId){
                subscriber.element = "#404eed"
            }
          })
      });

      state.session.on('publisherStopSpeaking', (event) => {
          const publisherId = state.publisher.stream.streamId
          const str_len = event.connection.connectionId.length
          if (publisherId.slice(-str_len) === event.connection.connectionId){
            state.BorderColor = "black"
          }
          state.subscribers.forEach((subscriber) => {

            // publisher의 event면 publisher만 변경
            // subscirber의 event면 subscriber 변경
           if (subscriber.stream.streamId.slice(-str_len) === event.connection.connectionId){
                subscriber.element = "black"
            }
          })
      });

      getToken(state.mySessionId).then((token) => {
        state.session
          .connect(token, { clientData: state.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = state.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: '640x480', // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            state.mainStreamManager = publisher;
            state.publisher = publisher;

            // --- Publish your stream ---

            state.session.publish(state.publisher);
            state.videoStatus = state.publisher.stream.videoActive;
            state.audioStatus = state.publisher.stream.audioActive;
          })
          .catch((error) => {
            console.log(
              'There was an error connecting to the session:',
              error.code,
              error.message
            );
          });
      });

      window.addEventListener('beforeunload', leaveSession);
    };

    const leaveSession = function () {
      if (state.session) state.session.disconnect();

      state.session = undefined;
      state.mainStreamManager = undefined;
      state.publisher = undefined;
      state.subscribers = [];
      state.OV = undefined;
      store.commit('root/Messages');
      window.removeEventListener('beforeunload', leaveSession);
    };

    const updateMainVideoStreamManager = function (stream) {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    const getToken = function (mySessionId) {
      return createSession(mySessionId).then((sessionId) =>
        createToken(sessionId)
      );
    };

    const createSession = function (sessionId) {
      return new Promise((resolve, reject) => {
        instance
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    };

    const createToken = function (sessionId) {
      return new Promise((resolve, reject) => {
        instance
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    };
    const sendMessage = function (message) {
      var messageData = {
        content: message,
        secretName: store.getters['root/getSecretName'],
      };
      state.session.signal({
        type: 'chat',
        data: JSON.stringify(messageData),
        to: [],
      });
    };

    const takeProfile = function () {
      const token = store.getters['root/getJWTToken'];
      store
        .dispatch('root/getProfile', token)
        .then((res) => {
          state.userName = res.data.nickName;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    const getTeamInfo = function () {
      store
        .dispatch('root/getTeamInfoDetail', route.params.conferenceId)
        .then((res) => {
          state.teamName = res.data.teamName;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    // 비디오 토글
    const changeVideoState = function () {
      state.videoStatus = !state.videoStatus;
      state.publisher.publishVideo(state.videoStatus);
    };

    // 오디오 토글
    const changeAudioState = function () {
      state.audioStatus = !state.audioStatus;
      state.publisher.publishAudio(state.audioStatus);
    };


    // 배경이미지 변경
    const onOpenBackImgDialog = function(){
      state.backImgDialogOpen = true
    }

    const onCloseBackImgDialog = function (){
      state.backImgDialogOpen = false
    };
    const onCloseCaptureImgDialog = function (){
      state.captureImgDialogOpen = false
    };
    const setBackImg = function(imgsrc){
      state.backImg = imgsrc;
    }
    // screen 레코딩
    const recordScreen = async function (){
      return await navigator.mediaDevices.getDisplayMedia({
        audio: true,
        video: { mediaSource: 'screen'}
    });
    }
    const createRecorder = function (stream, mimeType){
      // the stream data is stored in this array
      let recordedChunks = [];

      const mediaRecorder = new MediaRecorder(stream);

      mediaRecorder.ondataavailable = function (e) {
        if (e.data.size > 0) {
          recordedChunks.push(e.data);
        }
      };
      mediaRecorder.onstop = function () {
          state.recordState = false
          saveFile(recordedChunks);
          recordedChunks = [];
      };
      mediaRecorder.start(200); // For every 200ms the stream data will be stored in a separate chunk.
      return mediaRecorder;
    }
    // team 게시글에 저장
    const saveFile = function (recordedChunks){
      const blob = new Blob(recordedChunks, {
          type: 'video/mp4'
        });
      let filename = window.prompt('저장할 동영상의 이름을 입력하세요.')
      // const videoPath = URL.createObjectURL(blob)
      // const videoName = `${filename}.mp4`
      let formData = new FormData()
      formData.append('upfile', blob, 'file.mp4')
      formData.append('teamId', state.roomId)
      const today = new Date()
      const year = today.getFullYear()
      const month = ('0' + (today.getMonth() + 1)).slice(-2)
      const day = ('0' + today.getDate()).slice(-2)
      const dateString = `${year}-${month}-${day}`
      formData.append('uploadDate', dateString)
      store.dispatch('root/uploadVideo', { 'formData': formData, 'token': state.isLogin})
      .then(res => {
        setTimeout(() => {
          swal({
            title: '팀 게시판 영상 등록',
            text: '녹화된 영상이 팀 게시판에 등록 되었습니다.',
            icon: 'success',
            button: '확인',
          })
        }, 200)
        console.log(res)
      }).catch(err => {
        console.log(err)
      })
    }

    // 녹화 시작
    const startRecording = async function (){
      let stream = await recordScreen();
      let mimeType = 'video/mp4  '
      state.recordState = true
      mediaRecorder = createRecorder(stream, mimeType)
    }
    // 녹화 중단

    const takeSnapshot = function(){
      html2canvas(document.getElementById('chat-container')).then(canvas=>{
        console.log(canvas)

            canvas.getContext('2d').fillStyle=state.backImg;
            var img = canvas.getContext('2d').getImageData(0, 0, 1400, 700);
            var c = document.createElement('canvas');
            c.width = 1400;
            c.height = 700;

            c.getContext('2d').putImageData(img, 0, 0);

        c.toBlob(function(blob){
          const url = URL.createObjectURL(blob);
          console.log(blob);

          state.captureImg = url;
          state.captureImgDialogOpen=true;
        })

        // canvas.toBlob(blob => navigator.clipboard.write([new ClipboardItem({'image/png':blob})]))
        // canvas.toBlob(blob => navigator.clipboard.write([new ClipboardItem({'image/png':blob})]))
      })
    }

    const screenshot = function(){
      document.querySelector('body').classList.add('edit_cursor');

      var $bigcont = document.querySelector('#chat-container');
      var startX, startY;
      var height = window.innerHeight;
      var width = window.innerWidth;

      //배경을 어둡게 깔아주기 위한 div객체 생성
      var $screenBg = document.createElement('div');
      $screenBg.id='screenshot_background';
      $screenBg.style.borderWidth = '0 0 ' + height + 'px 0';

      //마우스 이동하면서 선택한 영역의 크기를 보여주기위한 div 객체 생성
      var $screenshot = document.createElement('div');
      $screenshot.id = 'screenshot';
      document.querySelector('body').appendChild($screenBg);
      document.querySelector('body').appendChild($screenshot);

      var selectArea = false;
      var body = document.querySelector('body');
      var test = document.querySelector('#screenshot_background');

      // window.addEventListener()
      const mousedown = function(e){
        e.preventDefault();
        selectArea = true;
        startX = e.clientX;
        startY = e.clientY;
        console.log(startX, startY);
        body.removeEventListener('mousedown', mousedown);

      }
      //마우스 누르는 이벤트 등록
      body.addEventListener('mousedown', mousedown);

      //클릭한 마우스 떼는 이벤트 함수
      var mouseup = function(e){
        selectArea = false;
        body.removeEventListener('mousemove', mousemove);
        $screenshot.parentNode.removeChild($screenshot);
        $screenBg.parentNode.removeChild($screenBg);
        var x = e.clientX;
        var y = e.clientY;
        var top = Math.min(y, startY);
        var left = Math.min(x, startX);
        var width = Math.max(x, startX) - left;
        var height = Math.max(y, startY) - top;
        html2canvas(document.body).then(
          function(canvas){
            //전체화면 캡쳐
            //선택 영역만큼 crop
            canvas.style.backgroundImage = state.backImg;
            canvas.style.backgroundSize= 'cover'
            console.log(canvas.style.backgroundImage);
            canvas.getContext('2d').fillStyle=state.backImg;
            var img = canvas.getContext('2d').getImageData(left, top, width, height);
            var c = document.createElement('canvas');
            c.width = width;
            c.height = height;

            c.getContext('2d').putImageData(img, 0, 0);
            save(c);
            //crop한 이미지 저장
          }
        );
        body.removeEventListener('mouseup', mouseup);
        document.querySelector('body').classList.remove('edit_cursor');
      }

      body.addEventListener('mouseup', mouseup);

      //마우스무브 이벤트 함수
      function mousemove(e){
        var x = e.clientX;
        var y = e.clientY;
        $screenshot.style.left = x;
        $screenshot.style.top = y;
        if(selectArea){
          var top = Math.min(y,startY);
          var right = width - Math.max(x, startX);
          var bottom = height - Math.max(y, startY);
          var left = Math.min(x, startX);
          $screenBg.style.borderWidth = top + 'px ' + right + 'px ' + bottom + 'px ' + left + 'px ';
        }
      }
      body.addEventListener('mousemove',mousemove);
      console.log(body);
    }

    const save = function(canvas){
      if(navigator.msSaveBlob){
        var blob = canvas.msToBlob();
        return navigator.msSaveBlob(blob, '파일명.jpg');
      }else{
        var el = document.getElementById('target');
        state.captureImg = canvas.toDataURL('image/jpeg');
          state.captureImgDialogOpen=true;
      }
    }
    // 회의실 상태 toggle
    const changeConfStateTrue = function (){
      console.log("도전")
      store.dispatch('root/changeConfStateTrue', { 'teamId': route.params.conferenceId })
      .then((res) => {
        console.log(res)
      }).catch((err) => {
        console.log(err)
      })
    }

    const changeConfStateFalse = function (){
      store.dispatch('root/changeConfStateFalse', { 'teamId': route.params.conferenceId })
      .then((res) => {
        console.log(res)
      }).catch((err) => {
        console.log(err)
      })
    }

    getTeamInfo();
    takeProfile()
    return { state, OPENVIDU_SERVER_URL, OPENVIDU_SERVER_SECRET, instance, joinSession, leaveSession, updateMainVideoStreamManager, getToken, createSession,
      createToken, sendMessage, closeSession, takeProfile, getTeamInfo, changeVideoState, changeAudioState, onOpenBackImgDialog, onCloseBackImgDialog,
      outSession, setBackImg, startRecording, recordScreen, saveFile,takeSnapshot, onCloseCaptureImgDialog, screenshot, changeConfStateTrue, changeConfStateFalse };
  },
};
</script>
