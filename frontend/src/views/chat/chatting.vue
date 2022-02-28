<template>
<div class="chatting-container">
  <!-- <div class="view login" v-if="state.username === '' || state.username === null">
    <form class="login-form" @submit.prevent="Login">
      <div class="form-inner">
        <h1>Login to FireChat</h1>
        <label for="username">Username</label>
        <input
          type="text"
          v-model="inputUsername"
          placeholder="Please enter your username..." />
        <input
          type="submit"
          value="Login" />
      </div>
    </form>
  </div> -->

  <!-- <div class="view chat" v-else> -->
  <div class="view chat" style="margin-bottom: 130px" >
    <header>
     <button class="logout" @click="Logout">채팅방 나가기</button>
      <h1>Welcome, {{ state.nickName }}</h1>
    </header>

    <section class="chat-box">
      <div
        v-for="message in state.messages"
        :key="message.key"
        :class="(message.username == state.nickName ? 'message current-user' : 'message')">
        <div class="message-inner">
          <div class="username">{{ message.username }}</div>
          <div class="content">{{ message.content }}</div>
        </div>
      </div>
    </section>

    <footer>
      <form @submit.prevent="SendMessage">
        <input
          type="text"
          v-model="inputMessage"
          placeholder="Write a message..." />
        <input
          type="submit"
          value="Send" />
      </form>
    </footer>
  </div>
</div>
</template>

<script>
import { reactive, onMounted, ref } from 'vue';
import db from '../../db';
import { useRoute, useRouter } from 'vue-router'
export default {

  name:'chatting',

  setup () {
    const route = useRoute()
    const router = useRouter()
    const inputUsername = ref('');
    const inputMessage = ref('');

    const state = reactive({
      chatId : route.params.session,
      username: route.params.userId,
      messages: [],
      nickName: route.params.nickName
    });

    const Login = () => {
      if (inputUsername.value != '' || inputUsername.value != null) {
        state.username = inputUsername.value;
        inputUsername.value = '';
      }
    }

    const Logout = () => {
      router.push({
        name: 'room-board',
        params:{
          roomId: route.params.roomId,
        }
      })
    }


    const SendMessage = () => {
      const messagesRef = db.database().ref(state.chatId);

      if (inputMessage.value === '' || inputMessage.value === null) {
        return;
      }

      const message = {
        username: state.nickName,
        content: inputMessage.value
      }

      messagesRef.push(message);
      inputMessage.value = '';
      const height = document.querySelector('.chatting-container').clientHeight;

      window.scrollTo(0, height);
    }



    onMounted(() => {
      const messagesRef = db.database().ref(state.chatId);

      messagesRef.on('value', snapshot => {
        const data = snapshot.val();
        let messages = [];

        Object.keys(data).forEach(key => {
          messages.push({
            id: key,
            username: data[key].username,
            content: data[key].content
          });
        });

        state.messages = messages;
      });
    });

    return {
      inputUsername,
      Login,
      state,
      inputMessage,
      SendMessage,
      Logout
    }
  }
}
</script>

<style lang="scss">
.chatting-container {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	width: 32vw;
	text-align: center;
	margin: auto;
}

.view {
	display: flex;
  border-radius: 24px 24px 0px 0px;
	justify-content: center;
	min-height: 100vh;
  margin-bottom: 30px;
	background-color: #78ccbb;

	&.login {
		align-items: center;
		.login-form {
			display: block;
			width: 100%;
			padding: 15px;

			.form-inner {
				display: block;
				background-color: #FFF;
				padding: 50px 15px;
				border-radius: 16px;
				box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2);

				h1 {
					color: #AAA;
					font-size: 28px;
					margin-bottom: 30px;
				}

				label {
					display: block;
					margin-bottom: 5px;
					color: #AAA;
					font-size: 16px;
					transition: 0.4s;
				}

				input[type="text"] {
					appearance: none;
					border: none;
					outline: none;
					background: none;

					display: block;
					width: 100%;
					padding: 10px 15px;
					border-radius: 8px;
					margin-bottom: 15px;

					color: #333;
					font-size: 18px;

					box-shadow: 0px 0px 0px rgba(0, 0, 0, 0);
					background-color: #F3F3F3;

					transition: 0.4s;

					&::placeholder {
						color: #888;
						transition: 0.4s;
					}
				}

				input[type="submit"] {
					appearance: none;
					border: none;
					outline: none;
					background: none;

					display: block;
					width: 100%;
					padding: 10px 15px;
					background-color: #78ccbb;
					border-radius: 8px;

					color: #FFF;
					font-size: 18px;
					font-weight: 700;
				}

				&:focus-within {
					label {
						color: #78ccbb;
					}

					input[type="text"] {
						background-color: #FFF;
						box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);

						&::placeholder {
							color: #666;
						}
					}
				}
			}
		}
	}

	&.chat {
		flex-direction: column;

		header {
			position: relative;
			display: block;
			width: 100%;
			padding: 50px 30px 10px;

			.logout {
				position: absolute;
				top: 15px;
				right: 15px;
				appearance: none;
				border: none;
				outline: none;
				background: none;

				color: #FFF;
				font-size: 18px;
				margin-bottom: 10px;
				text-align: right;
			}

			h1 {
				color: #FFF;
			}
		}

		.chat-box {
			border-radius: 24px 24px 0px 0px;
			background-color: #FFF;
			box-shadow: 0px 0px 12px rgba(100, 100, 100, 0.2);
			flex: 1 1 100%;
			padding: 30px;

			.message {
				display: flex;
				margin-bottom: 15px;

				.message-inner {
					.username {
						color: #888;
						font-size: 16px;
						margin-bottom: 5px;
						padding-left: 15px;
						padding-right: 15px;
					}

					.content {
						display: inline-block;
						padding: 10px 20px;
						background-color: #F3F3F3;
						border-radius: 999px;

						color: #333;
						font-size: 18px;
						line-height: 1.2em;
						text-align: left;
					}
				}

				&.current-user {
					margin-top: 30px;
					justify-content: flex-end;
					text-align: right;

					.message-inner {
						max-width: 75%;

						.content {
							color: #FFF;
							font-weight: 600;
							background-color: #78ccbb;
						}
					}
				}
			}
		}

		footer {
			position: sticky;
			bottom: 50px;
			background-color: #FFF;
			padding: 30px;
			box-shadow: 0px 0px 12px rgba(100, 100, 100, 0.2);

			form {
				display: flex;

				input[type="text"] {
					flex: 1 1 100%;

					appearance: none;
					border: none;
					outline: none;
					background: none;

					display: block;
					width: 100%;
					padding: 10px 15px;
					border-radius: 8px 0px 0px 8px;

					color: #333;
					font-size: 18px;

					box-shadow: 0px 0px 0px rgba(0, 0, 0, 0);
					background-color: #F3F3F3;

					transition: 0.4s;

					&::placeholder {
						color: #888;
						transition: 0.4s;
					}
				}

				input[type="submit"] {
					appearance: none;
					border: none;
					outline: none;
					background: none;

					display: block;
					padding: 10px 15px;
					border-radius: 0px 8px 8px 0px;

					background-color: #78ccbb;

					color: #FFF;
					font-size: 18px;
					font-weight: 700;
				}
			}
		}
	}
}
</style>
