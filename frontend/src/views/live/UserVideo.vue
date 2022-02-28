<template>
<div v-if="streamManager">
  <div class="d-flex flex-column align-items-center mx-3 my-2" id="user-video" :style="{ borderColor: this.borderColor}">
    <div class="video-mask">
      <ov-video :stream-manager="streamManager"/>
    </div>
    <div class="d-flex justify-content-center align-items-center" id="user-bar">
      <div>
       <p>이름: {{ clientData }}</p>
      </div>
    </div>
  </div>
</div>
</template>

<style scoped>
  #user-video{
    width: 22vw;
    height: 30vh;
    padding: 0.65vh;
    border-radius: 1.5vh;
    border-width: 0.5vh;
    border-style: solid;
    background-color:   rgb(88, 85, 85);
    box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.2);
  }

  #user-video p{
    display: inline-block;
    color: #131313;
    font-weight: bold;
  }

  #user-bar{
    width: 40vh;
    height: 6vh;
  }
  .video-mask{
    border-radius: 1.5vh;
    height: 32vh;
    overflow: hidden;
  }
</style>

<script>
import OvVideo from '@/views/live/OvVideo.vue';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
    borderColor: String,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
