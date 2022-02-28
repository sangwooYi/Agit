<template class="errorbody container">
        <!-- <div class='c'>
            <div class='_404'>Agit 초대장</div>
            <hr>
            <div class='_2'> {{state.team.teamName}}에서 </div>
            <div class='_2'> {{state.team.teamBoss}} 님이 당신을 초대합니다. </div>
            <div class='_2'>초대에 응하시겠습니까?</div>
            <a class='btn' @click = "clickacceptbtn()">수락</a>
            <a class='btn' @click = "clickrejectbtn()">거절</a>
        </div> -->

        <div id="final">
      <div
        data-aos="zoom-out"
        style="width: 18rem; margin: 30px auto;"
        class="d-flex flex-column align-items-center"

      >
        <img
            src="https://static8.depositphotos.com/1149789/974/i/450/depositphotos_9743915-stock-photo-vintage-old-paper.jpg"
            alt="..."
            class="my-4"
            height="700"
          />
        <div style="position: absolute; margin-top:25%;" class="d-flex flex-column align-items-center">
          <!-- <div v-if="state.isLogin === null" class="button-wrapper"> -->
            <!-- <el-button @click="clickSignup" style="background-color:white; color: #ffc107; font-family: 'East Sea Dokdo', cursive;">회원가입</el-button> -->
            <!-- <el-button @click="clickLogin " style="background-color:white; color: #ffc107;">로그인</el-button> -->
          <!-- </div> -->
          <div style="margin-top: 20px; font-size: 50px; font-family: 'East Sea Dokdo', cursive;">Agit Invitation</div>
          <p style="font-family: 'East Sea Dokdo', cursive; font-size: 25px;">비밀의 공간으로 당신을 초대합니다.</p>
          <p style="font-family: 'East Sea Dokdo', cursive; font-size: 25px;">{{state.team.teamName}}에서 </p>
          <p style="font-family: 'East Sea Dokdo', cursive; font-size: 30px;"> {{state.team.teamBoss}} 님이</p>
          <p style="font-family: 'East Sea Dokdo', cursive; font-size: 25px;">당신을 초대합니다.</p>
          <p style="font-family: 'East Sea Dokdo', cursive; font-size: 25px;"> 초대에 응하시겠습니까?</p>
          <div style="float: left;">
            <a class='btn' style="font-family: 'East Sea Dokdo', cursive; font-size: 30px;" @click = "clickacceptbtn()">수락</a>
            <a class='btn' style="font-family: 'East Sea Dokdo', cursive; font-size: 30px;"  @click = "clickrejectbtn()">거절</a>
          </div>


          <div style="width:90%; height: 30%; margin-top: 12%;" class="d-flex flex-column align-items-center">
            <div class="candle" style="margin-top: 10px;">
              <span class="glow"></span>
              <span class="flames"></span>
              <span class="thread"></span>
            </div>
          </div>


        </div>
      </div>
    </div>
</template>

<script>
import {onBeforeMount, reactive,computed} from 'vue'
import { useRoute, useRouter} from 'vue-router'
import { useStore } from 'vuex'
export default {
props:{
  roomId:{
    type: Number,
  }
},
setup() {
  const store = useStore()
  const route = useRoute();
  const router = useRouter();
  const state = reactive({
      team: {
        teamName: '',
        teamBoss: '',
      },
      isLogin: computed(() => store.getters['root/getJWTToken'])
    })
  const getTeamDetail = function(){
      store.dispatch('root/getTeamInfoDetail', route.params.roomId)
      .then(function(result){
        console.log(result.data);
        state.team = result.data;
        console.log(state.team.teamName);
        console.log(state.team.teamBoss);
      })
    }

  const clickrejectbtn = function(){
    console.log('check');
    store.dispatch('root/rejectTeamMember', {teamId: route.params.roomId, token:state.isLogin} )
      .then(function(result){
        console.log(result.data);
        console.log(result);
        swal({
          title: "초대거절",
          text: "초대를 거절하였습니다.",
          icon: "success",
          button: "확인",
        });
        router.push({
          name: 'intro'
      })
      }).catch(function(err){
        console.log(err.response)

      })

  }

  const clickacceptbtn = function(){
    console.log('check');
    store.dispatch('root/confirmTeamMember', {teamId: route.params.roomId, token:state.isLogin} )
      .then(function(result){
        console.log(result.data);
        console.log(result);
        swal({
          title: "초대승인",
          text: "초대를 승인하였습니다.",
          icon: "success",
          button: "확인",
        });
        router.push({
          name: 'room-board',
          params: {
            roomId: route.params.roomId,
          },
      })
      }).catch(function(err){
        if(err.response.data.statusCode==1006){
          swal({
          title: "초대승인",
          text: "수용가능한 팀멤버인원을 초과하였습니다.",
          icon: "error",
          button: "확인",
        });
        }
        console.log(err.response)

      })
  }

    onBeforeMount(()=>{
      console.log(route.params.roomId);
      getTeamDetail();
    })

    return {getTeamDetail, state,clickacceptbtn,clickrejectbtn};
  }
}
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Tangerine);
        .errorbody{
                background: #33cc99;
                color:#fff;
                font-family: 'Open Sans', sans-serif;
                max-height:700px;
                overflow: hidden;
            }
            .c{
                text-align: center;
                display: block;
                position: relative;
                width:80%;
                margin:100px auto;
            }
            ._404{
                font-size: 80px;
                position: relative;
                display: inline-block;
                z-index: 2;
                height: 120px;
                letter-spacing: 15px;
            }
            ._1{
                text-align:center;
                display:block;
                position:relative;
                letter-spacing: 12px;
                font-size: 4em;
                line-height: 80%;
            }
            ._2{
                text-align:center;
                display:block;
                position: relative;
                font-size: 20px;
            }
            .text{
                font-size: 70px;
                text-align: center;
                position: relative;
                display: inline-block;
                margin: 19px 0px 0px 0px;
                /* top: 256.301px; */
                z-index: 3;
                width: 100%;
                line-height: 1.2em;
                display: inline-block;
            }


            .btn{
                background-color: transparent !important;
                position: relative;
                display: inline-block;
                width: 100px;
                padding: 5px;
                z-index: 5;
                font-size: 25px;
                margin:0 auto;
                color:#A85247;
                text-decoration: none;
                margin-right: 10px;
            }



            @-webkit-keyframes moveclouds {
                0% {margin-left: 1000px;}
                100% {margin-left: -1000px;}
            }
            @-moz-keyframes moveclouds {
                0% {margin-left: 1000px;}
                100% {margin-left: -1000px;}
            }
            @-o-keyframes moveclouds {
                0% {margin-left: 1000px;}
                100% {margin-left: -1000px;}
            }


@keyframes blink {
  78% {
    color: inherit;
    text-shadow: inherit;
  }
  79%{
     color: #333;
  }
  80% {

    text-shadow: none;
  }
  81% {
    color: inherit;
    text-shadow: inherit;
  }
  82% {
    color: #333;
    text-shadow: none;
  }
  83% {
    color: inherit;
    text-shadow: inherit;
  }
  92% {
    color: #333;
    text-shadow: none;
  }
  92.5% {
    color: inherit;
    text-shadow: inherit;
  }
}

.candle {
    width: 10em;
    height: 10em;
    font-size: 7px;
    background: linear-gradient(
        orange,
        darkorange,
        sienna,
        saddlebrown 50%,
        rgba(0, 0, 0, 0.6)
    );
    box-shadow:
        inset 2em -3em 5em rgba(0, 0, 0, 0.4),
        inset -2em 0 5em rgba(0, 0, 0, 0.4);
    border-radius: 10em / 4em;
    position: relative;
    display: flex;
    justify-content: center;
    top: 10em;
}

.candle::before {
    content: '';
    position: absolute;
    width: inherit;
    height: 2em;
    border: 0.2em solid darkorange;
    border-radius: 50%;
    box-sizing: border-box;
    background: radial-gradient(
        #444,
        orange,
        saddlebrown,
        sienna,
        darkorange
    );
    filter: opacity(0.7);
}

.thread {
    position: absolute;
    width: 0.6em;
    height: 3.6em;
    top: -1.8em;
    background: linear-gradient(
        #111,
        black,
        orange 90%
    );
    border-radius: 40% 40% 0 0;
}

.flames {
    position: absolute;
    width: 2.4em;
}

.flames::before {
    content: '';
    position: absolute;
    width: inherit;
    height: 6em;
    background-color: royalblue;
    top: -4.8em;
    border-radius: 50% 50% 35% 35%;
    border: 0.2em solid dodgerblue;
    box-sizing: border-box;
    filter: opacity(0.7);
}

.flames::after {
    content: '';
    position: absolute;
    width: inherit;
    height: 12em;
    top: -12em;
    background: linear-gradient(white 80%, transparent);
    border-radius: 50% 50% 20% 20%;
    box-shadow: 0 -0.6em 0.4em darkorange;
    animation:
        enlarge 5s linear infinite,
        move 6s linear infinite;
}

@keyframes move {
    0%, 100% {
        transform: rotate(2deg);
    }

    50% {
        transform: rotate(-2deg);
    }
}

@keyframes enlarge {
    0%, 100% {
        height: 12em;
        top: -12em;
    }

    50% {
        height: 14em;
        top: -13em;
    }
}

.glow {
    position: absolute;
    width: 10em;
    height: 18em;
    background-color: orangered;
    border-radius: 50%;
    top: -17em;
    filter: blur(6em);
    animation: okay 100ms infinite;
}

@keyframes okay {
    to {
        filter: blur(6em) opacity(0.8);
    }
}



</style>
