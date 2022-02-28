<template>
  <video id="video" autoplay></video>
  <button id="start" @click='startRecording'>start</button>
  <button id="stop" @click='stopCapture'>stop</button>
  <button id="snapshot" @click='startCapture' >snapshot</button>
  <img id="snapshotimg" src='http://localhost:8083/545edd36-1e41-41ed-b8f7-40af2ed79233' style="width:100px; height:100px;"/>
</template>

<script>
import {reactive, computed} from 'vue';
import { useStore } from 'vuex';
export default {
setup(){
  const store = useStore();
  const state = reactive({
    imgsrc:'',
    captureStream:'',
    videoElement:document.querySelector('#video'),
    isLogin: computed(() => store.getters['root/getJWTToken']),
  })
  let mediaRecorder
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
          saveFile(recordedChunks);
          recordedChunks = [];
      };
      mediaRecorder.start(200); // For every 200ms the stream data will be stored in a separate chunk.
      return mediaRecorder;
    }
    // team 게시글에 저장
    const saveFile = function (recordedChunks){
      const blob = new Blob(recordedChunks, {
          type: 'image/png'
        });
      let filename = window.prompt('저장할 동영상의 이름을 입력하세요.')
      // const videoPath = URL.createObjectURL(blob)
      // const videoName = `${filename}.mp4`
      console.log(blob);
      const url = window.URL.createObjectURL(blob);
      console.log(url);
      let formData = new FormData()
      formData.append('upfile', blob, 'file.mp4')
      formData.append('teamId', state.roomId)
      const today = new Date()
      const year = today.getFullYear()
      const month = ('0' + (today.getMonth() + 1)).slice(-2)
      const day = ('0' + today.getDate()).slice(-2)
      const dateString = `${year}-${month}-${day}`
      formData.append('uploadDate', dateString)
      console.log(formData);
      // store.dispatch('root/uploadVideo', { 'formData': formData, 'token': state.isLogin})
      // .then(res => {
      //   setTimeout(() => {
      //     swal({
      //       title: '팀 게시판 영상 등록',
      //       text: '녹화된 영상이 팀 게시판에 등록 되었습니다.',
      //       icon: 'success',
      //       button: '확인',
      //     })
      //   }, 200)
      //   console.log(res)
      // }).catch(err => {
      //   console.log(err)
      // })
    }

     // 녹화 시작
    const startRecording = async function (){
      let stream = await recordScreen();
      let mimeType = 'video/mp4  '
      mediaRecorder = createRecorder(stream, mimeType)
    }

    const startCapture = async function() {

        const videoElement = document.querySelector('#video')
        const startButton = document.querySelector('#start')
        const stopButton = document.querySelector('#stop')
        const snapshotButton = document.querySelector('#snapshot')
        console.log(videoElement);
        const displayMediaOptions = { audio: false, video: { cursor: 'always' } }
        state.captureStream = await navigator.mediaDevices.getDisplayMedia(displayMediaOptions)
        console.log(state.captureStream);
        console.log(state.captureStream);
        videoElement.srcObject = state.captureStream;

        const track = videoElement.srcObject.getVideoTracks()[0]
        const imageCapture = new ImageCapture(track)
        const image = await imageCapture.grabFrame()
        let blob = new Blob([new ArrayBuffer(image)], { type: 'image/png' });
              const url = window.URL.createObjectURL(blob);
              console.log(blob);
              console.log(URL.createObjectURL(blob))
              state.imgsrc = window.URL.createObjectURL(blob)
              console.log(state.imgsrc);

              const snapshotimg = document.querySelector('#snapshotimg');
              snapshotimg.src = state.imgsrc;

      let formData = new FormData()
            formData.append('upfile', blob, 'file.png')
            formData.append('teamId', 1)
            const today = new Date()
            const year = today.getFullYear()
            const month = ('0' + (today.getMonth() + 1)).slice(-2)
            const day = ('0' + today.getDate()).slice(-2)
            const dateString = `${year}-${month}-${day}`
            formData.append('uploadDate', dateString)
            console.log(formData);
            store.dispatch('root/uploadImage', { 'formData': formData, 'token': state.isLogin})
            .then(res => {
              setTimeout(() => {
                swal({
                  title: '팀 게시판 사진 등록',
                  text: '사진이 팀 게시판에 등록 되었습니다.',
                  icon: 'success',
                  button: '확인',
                })
              }, 200)
              console.log(res)
            }).catch(err => {
              console.log(err)
            })

      // console.log(URL.createObjectURL(image));


    }

  // 화면캡쳐를 중지하는 함수
    const stopCapture = async function(){
      const videoElement = document.querySelector('#video')
        const startButton = document.querySelector('#start')
        const stopButton = document.querySelector('#stop')
        const snapshotButton = document.querySelector('#snapshot')
      console.log(state.captureStream);
      const tracks = videoElement.srcObject.getTrack();
      tracks.forEach((track) => track.stop())
      state.captureStream = null


      takeSnapshot();
    }

  // 스냅샷을 찍는 함수
    const takeSnapshot = async function () {
 const videoElement = document.querySelector('#video')
      const track = videoElement.srcObject.getVideoTracks()[0]
      const imageCapture = new ImageCapture(track)
      const image = await imageCapture.grabFrame()
      // ...

       let blob = new Blob([new ArrayBuffer(image)], { type: "image/png" });
      const url = window.URL.createObjectURL(blob);
      console.log(blob);
      console.log(URL.createObjectURL(blob))
      state.imgsrc = window.URL.createObjectURL(blob)
      console.log(state.imgsrc);

      const snapshotimg = document.querySelector('#snapshotimg');
      snapshotimg.src = state.imgsrc;
    };
    return {takeSnapshot,stopCapture,startCapture,state, startRecording,  };
    }


  // 화면캡쳐(video)를 시작하는 함수
}

</script>

<style>

</style>
