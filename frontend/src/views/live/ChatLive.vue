<template>
    <div>
    <div class="chat-box p-2 d-flex flex-column mt-1">
      <div class="header text-left d-flex justify-content-center align-items-center">
        <span class="title">
          채팅
        </span>
        <!-- <button
          class="btn close-btn m-3"
          @click="toggleChatPanel"
        >
          <i class="fas fa-times"></i>
        </button> -->
      </div>
      <!-- 채팅 내역 -->
      <div
        id="chat-area"
        :height="state.form.chatHeight"
      >
        <div
          class="mt-2 mx-2 text-left message"
          v-for="(message, i) of state.messages"
          :key="i"
        >
          <div class="message-title">
            <span class="mr-2 message-header">{{ message.sender }} :</span>
            <span class="m-3 message-header">{{ message.time }}</span>
          </div>
          <div>
            {{ message.message }}
          </div>
        </div>
      </div>
      <div class="d-flex mt-auto text-center">
        <div class="mx-1">
          <input
            class="text-box"
            v-model="state.form.message"
            @keyup.enter="clickSendMessage"
          >
        </div>
        <div class="mx-1">
          <button
            class="send-btn"
            @click="clickSendMessage"
          >
            <i class="fas fa-paper-plane color-g text-center"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: "ChatLive",

  setup( props , { emit }){
  const store = useStore()


  const state = reactive({
    form:{
      message: "",
      chatHeight: "30vh",
    },
    messages: computed(() => store.getters['root/getMessages']),
  })

  const clickSendMessage = function (){
    if (state.form.message.trim()){
      emit("sendMessage", state.form.message)
      state.form.message = ""
      setTimeout(() => {
        let chatDiv = document.getElementById("chat-area")
        chatDiv.scrollTo(0, chatDiv.scrollHeight)
      }, 50)
     }
    }

    return {state, clickSendMessage}

  }
}
</script>
<style scoped>
.chat-box {
  width: 23vw;
  height: 72vh;
  margin-right: 2vw;
  border-radius: 15px;
  border-width: 0.35vh;
  border-style: solid;
  border-color: black;
  background-color: rgb(85, 174, 121);
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.25);
}
.color-g{
  color: #323f45;
}

.header {
  position: relative;
  background-color: rgb(70, 148, 101);
}

.text-box {
  background-color: #a7a6a6;
  width: 18vw;
  border-radius: 12px;
  color: black;
  padding-left: 0.8vw;
  height: 4.8vh;
}

.text-box:focus {
  outline: none;
}

.send-btn {
  border-radius: 1.5vh;
  height: 4.8vh;
  width: 2.5vw;
  color: #f6f6f6;
}

.title {
  padding-left: 5%;
  font-family: 'Jua' !important;
  font-size: 1.0rem !important;
  color: black;
}

.header {
  width: 100%;
  border-radius: 20px;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.45);
  height: 4vh;
}
.message-title {
  font-size: 0.8rem;
}

.message-header {
  font-family: 'Nanum Gothic', sans-serif;
}

.message {
  color: black;
}


#chat-area {
  overflow-y: auto;
}

#chat-area::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

#chat-area::-webkit-scrollbar-track {
  background: #37474F;
}

#chat-area::-webkit-scrollbar-corner {
  background: #37474F;
}

#chat-area::-webkit-scrollbar-thumb {
  background:  #a7a6a6;
}

#chat-area::-webkit-scrollbar-button {
  background-color: #37474F;
  height: 0;
}
</style>
