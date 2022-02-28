<template>
  <div style="dispaly: flex; justify-content: center; align-items:center; width: 99.2%; margin-bottom: 20px">
    <div v-if = "state.infos.length >= 3">
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item v-for="info in state.infos" :key="info.endDate">
          <h5>
            <br>
            팀: {{ info.teamName }}<br><br>
            약속 제목: {{ info.eventTitle }}<br><br>
            약속 날짜: {{ info.endDate.slice(5, 7) }}월 {{ info.endDate.slice(8, 11) }}일<br><br>
            D-day: {{ info.dday }}일 <br>
          </h5>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div v-else-if = "state.infos.length === 2">
      <el-carousel height="200px" direction="vertical" :autoplay="false">
        <el-carousel-item v-for="info in state.infos" :key="info.startDate">
        <h5>
          <br>
          팀: {{ info.teamName }} <br><br>
          약속 제목: {{ info.eventTitle }} <br>
          약속 내용: {{ info.eventContent }} <br><br>
          약속 날짜: {{ info.endDate.slice(5, 7) }}월 {{ info.endDate.slice(8, 11) }}일<br>
          D-day: {{ info.dday }}일 <br>
        </h5>
      </el-carousel-item>
    </el-carousel>
    </div>
    <div v-else-if = "state.infos.length === 1">
      <el-carousel height="200px" direction="vertical" :autoplay="false">
        <el-carousel-item v-for="info in state.infos" :key="info.startDate">
        <h5>
          <br>
          팀: {{ info.teamName }} <br><br>
          약속 제목: {{ info.eventTitle }} <br>
          약속 내용: {{ info.eventContent }} <br><br>
          약속 날짜: {{ info.endDate.slice(5, 7) }}월 {{ info.endDate.slice(8, 11) }}일<br>
          D-day: {{ info.dday }}일 <br>
        </h5>
      </el-carousel-item>
    </el-carousel>

    </div>
    <!-- 확인필요! -->
    <div v-else-if = "state.infos.length === 0">
      <el-empty :image-size="80"><p><i class="em em-alarm_clock" style="margin-bottom:4px" aria-role="presentation" aria-label="ALARM CLOCK"></i> 일정을 추가해주세요</p></el-empty>
    </div>
  </div>
  <div style="width:100%;">
    <div style="width: 92vw;" class="d-flex flex-row">
      <div id="scape">
        <div class="landing" v-if="value==true">
          <div id="clock" style="z-index:2; top:30px;" class="mx-0" >
            <p class="date">{{ state.clock.date }}</p>
            <el-switch
              v-model="value"
              size="large"
              active-text="낮"
              active-color="#20B2AA"
              inactive-text="밤"
              inactive-color="#20B2AA"
            />
            <!-- <div class="box sb5 rounded-pill" style="z-index:2; left: 30px;">오늘은 일정이<br> 없어요!</div> -->

          </div>



          <div class="mountain-scene">
            <!-- <div class="box sb5 rounded-pill" style="z-index:2; top:16vh; left: 420px;">오늘은 일정이<br> 없어요!</div> -->
            <div id="clouds">
              <div class="cloud" style="z-index: 0"></div>
              <div class="cloud" style="z-index: 0"></div>
              <div class="cloud" style="z-index: 0"></div>
              <div class="cloud" style="z-index: 0"></div>

            </div>
            <!-- background mountains -->
            <div class="small-mountains">
              <div class="tri"></div>
              <div class="tri"></div>
              <div class="tri"></div>
            </div>
            <div class="tall-mountains">
              <div class="tri2"></div>
              <div class="tri2"></div>
              <div class="tri2"></div>
            </div>
            <!-- foreground mountains -->
            <div class="tiny-mountains">
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
            </div>
          </div>
          <div class="ground d-flex justify-content-evenly">
            <div>
              <el-button type="text" @click="state.beforeDialogOpen = true" style="margin-left:100px; font-size: 1.5rem; color: #13C7A3"
                >이전 약속</el-button
              >
            </div>
              <div class="page-wrapper" style="margin-right: 140px;">
                <div class="box sb5 rounded-pill" style="z-index:2; left: 270px; bottom: 200px;">저를<br>눌러보세요:)</div>
                <div class="box sb4 rounded-pill" style="margin-bottom: 200px;" v-if="todayschedule.length >= 1">오늘 일정이<br>{{ todayschedule.length }}개 있어요!</div>
                <div class="box sb4 rounded-pill" style="margin-bottom: 200px;" v-else-if="todayschedule.length == 0">오늘은 일정이<br> 없어요!</div>
                <div class="loader">
                  <div v-if="jcolor == 1" class="jelly" @click="state.colorDialogOpen = true">

                    <div class="body1"></div>

                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 2" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body2"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 3" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body3"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 4" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body4"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 5" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body5"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div class="shadow"></div>

                </div>
                <div class="box sb3 rounded-pill" style="margin-bottom: 250px; margin-left:-20px;"  v-if="todayschedule.length == 0">푹 쉬세요 <i style="margin-bottom:3px" class="em em-stuck_out_tongue_closed_eyes" aria-role="presentation" aria-label="FACE WITH STUCK-OUT TONGUE AND TIGHTLY-CLOSED EYES"></i></div>
                <div class="box sb3 rounded-pill" style="margin-bottom: 250px; margin-left:-20px;" v-else-if="1 <= todayschedule.length && todayschedule.length <= 2">좋은 추억<br>쌓으세요 <i style="margin-bottom:3px" class="em em-sunglasses" aria-role="presentation" aria-label="SMILING FACE WITH SUNGLASSES"></i></div>
                <div class="box sb3 rounded-pill" style="margin-bottom: 250px; margin-left:-20px;" v-else-if="todayschedule.length >= 3">오늘은<br>바쁘네요 <i style="margin-bottom:3px" class="em em-cry" aria-role="presentation" aria-label="CRYING FACE"></i></div>
              </div>
            <div>
              <el-button type="text" @click="state.afterDialogOpen = true" style="margin-right:100px; font-size: 1.5rem; color: #13C7A3"
                >다음 약속</el-button>
            </div>
          </div>
        </div>
        <div class="landing2" v-else-if="value==false">
          <div id="clock" style="z-index:2; top:30px;" class="mx-0" >
            <p class="date">{{ state.clock.date }}</p>
            <el-switch
              v-model="value"
              size="large"
              active-text="낮"
              inactive-text="밤"
            />
          </div>
          <div class="mountain-scene">
            <div id="clouds">
              <div class="cloud" style="z-index: 0"></div>
              <div class="cloud" style="z-index: 0"></div>
              <div class="cloud" style="z-index: 0"></div>
              <div class="cloud" style="z-index: 0"></div>


            </div>
            <!-- background mountains -->
            <div class="small-mountains">
              <div class="tri"></div>
              <div class="tri"></div>
              <div class="tri"></div>
            </div>
            <div class="tall-mountains">
              <div class="tri2"></div>
              <div class="tri2"></div>
              <div class="tri2"></div>
            </div>
            <!-- foreground mountains -->
            <div class="tiny-mountains">
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
              <div class="tri3"></div>
            </div>
          </div>
          <div class="ground d-flex justify-content-evenly">
            <div>
              <el-button type="text" @click="state.beforeDialogOpen = true" style="margin-left:100px; font-size: 1.5rem; color: #13C7A3"
                >이전 약속</el-button
              >
            </div>
              <div class="page-wrapper" style="margin-right: 140px;">
                <div class="box sb5 rounded-pill" style="z-index:2; left: 270px; bottom: 200px;">저를<br>눌러보세요:)</div>
                <div class="box sb4 rounded-pill" style="margin-bottom: 200px;" v-if="todayschedule.length >= 1">오늘 일정이<br>{{ todayschedule.length }}개 있어요!</div>
                <div class="box sb4 rounded-pill" style="margin-bottom: 200px;" v-else-if="todayschedule.length == 0">오늘은 일정이<br> 없어요!</div>
                <div class="loader">
                  <div v-if="jcolor == 1" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body1"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 2" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body2"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 3" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body3"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 4" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body4"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div v-else-if="jcolor == 5" class="jelly"  @click="state.colorDialogOpen = true">
                    <div class="body5"></div>
                    <div class="eye"></div>
                    <div class="eye"></div>
                    <div class="mouth"></div>
                  </div>
                  <div class="shadow"></div>
                </div>
                <div class="box sb3 rounded-pill" style="margin-bottom: 250px; margin-left:-20px;"  v-if="todayschedule.length == 0">푹 쉬세요 <i style="margin-bottom:3px" class="em em-stuck_out_tongue_closed_eyes" aria-role="presentation" aria-label="FACE WITH STUCK-OUT TONGUE AND TIGHTLY-CLOSED EYES"></i></div>
                <div class="box sb3 rounded-pill" style="margin-bottom: 250px; margin-left:-20px;" v-else-if="1 <= todayschedule.length && todayschedule.length <= 2">좋은 추억<br>쌓으세요 <i style="margin-bottom:3px" class="em em-sunglasses" aria-role="presentation" aria-label="SMILING FACE WITH SUNGLASSES"></i></div>
                <div class="box sb3 rounded-pill" style="margin-bottom: 250px; margin-left:-20px;" v-else-if="todayschedule.length >= 3">오늘은<br>바쁘네요 <i style="margin-bottom:3px" class="em em-cry" aria-role="presentation" aria-label="CRYING FACE"></i></div>
              </div>
            <div>
              <el-button type="text" @click="state.afterDialogOpen = true" style="margin-right:100px; font-size: 1.5rem; color: #13C7A3"
                >다음 약속</el-button>
            </div>
            <!-- <div class="d-flex justify-content-center">
              <el-button type="text" style="min-height:10px; padding: 10px; font-size: 1.0rem; margin-top:100px" @click="state.termsDialogOpen = true">이용약관</el-button>
            </div> -->
          </div>
        </div>
      </div>
      <div class="iphone" style="margin-left: -50px">
        <div class="header" >
          <div class="order-summary" style="position: relative; margin-top: 130px;">
            <div class="order-status">ID: {{ state.profileinfo.userId }}</div>
             <!-- <div class="order-date">
            닉네임: {{ state.profileinfo.nickName }}<el-button type="text" style=" min-height: 15px; padding: 10px; font-size: 1.5rem; z-index:9000;" @click="state.nicknameDialogOpen = true">수정하기</el-button>
            </div>
            <div class="order-day">
            {{ state.profileinfo.nickName }}의 생일: {{ state.profileinfo.birthDay }}
            </div> -->
            <div>
              <div class="order-date" v-if="state.profileinfo.nickName == ''">
              닉네임: 오리너구리<el-button type="text" style=" min-height: 15px; padding: 10px; font-size: 1.5rem;" @click="state.nicknameDialogOpen = true">수정하기</el-button>
              </div>
              <div class="order-date" v-else-if="state.profileinfo.nickName != null">
              닉네임: {{ state.profileinfo.nickName }}<el-button type="text" style=" min-height: 15px; padding: 10px; font-size: 1.5rem;" @click="state.nicknameDialogOpen = true">수정하기</el-button>
              </div>
            </div>
            <div>
            <div class="order-day" v-if="state.profileinfo.nickName == ''">
            오리너구리의 생일: {{ state.profileinfo.birthDay }}
            <div class="order-day">
              <el-button type="text" style=" min-height:15px;font-size: 1.5rem;" @click="state.termsDialogOpen = true"><i style="margin-bottom: 5px" class="em em-book" aria-role="presentation" aria-label="OPEN BOOK"></i> 이용약관 확인하기</el-button>
            </div>
            </div>
            <div class="order-day" v-else-if="state.profileinfo.nickName != null">
            {{ state.profileinfo.nickName }}의 생일: {{ state.profileinfo.birthDay }}
            <div class="order-day">
              <el-button type="text" style=" min-height:15px;font-size: 1.5rem;" @click="state.termsDialogOpen = true"><i style="margin-bottom: 5px" class="em em-book" aria-role="presentation" aria-label="OPEN BOOK"></i> 이용약관 확인하기</el-button>
            </div>
            </div>
            </div>

          </div>
        </div>
        <div class="hero-img-container rounded-3" style="margin-top: 60px">
          <img :src=state.profileinfo.profileImg v-if="state.profileinfo.profileImg!=null" class="hero-img rounded-3">
          <img :src="require('@/assets/images/agit_logo.png')" v-else-if="state.profileinfo.profileImg==null" class="hero-img rounded-3">
        </div>
        <div class="d-flex justify-content-center">
          <el-button type="text" style="min-height:15px; padding: 10px; font-size: 1.5rem;"  @click="state.photoDialogOpen = true"><i class="em em-camera_with_flash" style="margin-bottom:6px" aria-role="presentation" aria-label="CAMERA WITH FLASH"></i> 이미지 변경</el-button>
        </div>
        <!-- <div>
          <img :src=state.check alt="">
          <p>{{ state.check }}</p>
        </div> -->
        <!-- <div class="d-flex justify-content-center" style="margin-top:10%; position: relative;">
          <el-button type="text" style=" min-height:15px; padding: 10px; font-size: 1.5rem;" @click="state.termsDialogOpen = true"><i class="em em-book" aria-role="presentation" aria-label="OPEN BOOK"></i> 이용약관 확인하기></el-button>
        </div> -->
      </div>
    </div>

  </div>

  <before-dialog
    :open="state.beforeDialogOpen"
    :info="state.pros.beforepro[0]"
    :teamP="state.beforeteamPicture"
    @closeBeforeDialog="onCloseBeforeDialog" />
  <after-dialog
    :open="state.afterDialogOpen"
    :info="state.pros.afterpro[0]"
    :teamP="state.afterteamPicture"
    @closeAfterDialog="onCloseAfterDialog" />
  <nickname-dialog
    :open="state.nicknameDialogOpen"
    :info="state.profileinfo"
    @closeNicknameDialog="onCloseNicknameDialog"
    @edit-nickname="editNickname"
    />
  <birthday-dialog
  :open="state.birthdayDialogOpen"
  :info="state.profileinfo"
  @closeBirthdayDialog="onCloseBirthdayDialog"
  @create-birthday="createBirthday"
  />
  <terms-dialog
  :open="state.termsDialogOpen"
  @closeTermsDialog="onCloseTermsDialog"
  />
  <photo-dialog
    :open="state.photoDialogOpen"
    @closePhotoDialog="onClosePhotoDialog"
    @edit-my-photo="editMyPhoto"
  />
  <color-dialog
   :open="state.colorDialogOpen"
   @closeColorDialog="onCloseColorDialog"
   @edit-color="editColor"
  />

</template>

<script>
import { reactive, ref, onBeforeMount, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import BeforeDialog from './components/before-dialog.vue'
import AfterDialog from './components/after-dialog.vue'
import NicknameDialog from './components/nickname-dialog.vue'
import BirthdayDialog from './components/birthday-dialog.vue'
import TermsDialog from './components/terms-dialog.vue'
import PhotoDialog from './components/photo-dialog.vue'
import _ from 'lodash'
import ColorDialog from './components/color-dialog.vue'

export default {
  name: 'schedule',
  components: {
    BeforeDialog,
    AfterDialog,
    NicknameDialog,
    BirthdayDialog,
    TermsDialog,
    PhotoDialog,
    ColorDialog,

  },

  setup() {

  // let beforepro = ''
  // let afterpro = ''
  const store = useStore()
  const router = useRouter()
  const state = reactive({
    infos: {
        eventTitle: '',
        eventContent: '',
        teamName: '',
        startDate: '1970-01-01',
        endDate: '1970-01-01',
        dday: '1970-01-01',
        teamId: '',
      },
    profileinfo: {
        birthDay: '1970-01-01',
        name: '',
        nickName: '',
        userId: '',
        id: '',
        cdate: '1970-01-01',
        emailType: '',
        password: '',
        profileImg:'https://drive.google.com/uc?id=15iXUI6DkRr5Zcp0yH5uF2U47ycr-WzUY'
      },
    clock : {
        time: '',
        date: '',
    },
    schedulelength: 0,
    pros : {
      beforepro: [],
      afterpro: []
    },
    afterDialogOpen: false,
    beforeDialogOpen: false,
    nicknameDialogOpen : false,
    birthdayDialogOpen : false,
    termsDialogOpen : false,
    photoDialogOpen : false,
    colorDialogOpen : false,
    beforeteamPicture : '',
    afterteamPicture : '',

    check: JSON.parse(localStorage.getItem('photoUrl')),
    isLogin: computed(() => store.getters['root/getJWTToken']),

  })
  const beforeschedule = []
  const afterschedule = []
  const todayschedule = []
  let beforeday = ''
  let afterday = ''
  // let beforeteamId = ''

  var week = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
  var timerID = setInterval(updateTime, 1000);
  const value = ref(true)
  var jcolor = ref(1)


  const checkUserState = function(){
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
      }}


  const takeSchdule = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getSchedule', token)
      .then(res => {
        let today = new Date().toISOString().slice(0,10)
        state.schedulelength = res.data.length
        state.infos = res.data.slice().reverse()
        state.infos = _.sortBy(state.infos, 'dday')
        console.log(state.infos)
        for (var i = 0; i < state.schedulelength; i++) {
          let tempendday = state.infos[i].endDate
          let enddayday = Number(tempendday.slice(8,10))
          if (state.infos[i].dday == 0) {
            todayschedule.push(state.infos[i])
            console.log(todayschedule)
            console.log('확인해봐')
          }
          else {
            console.log('No')
          }
          let tempday = state.infos[i].dday
          if (0 >= tempday) {
            beforeschedule.push(state.infos[i])
          } else {
            afterschedule.push(state.infos[i])
            console.log(afterschedule)
            console.log('yesss')
          }
        }
        let beforemax = -31
        if (beforeschedule.length >= 1) {
          for (var b = 0; b < beforeschedule.length; b++) {
            if (beforeschedule[b].dday >= beforemax) {
              beforemax = beforeschedule[b].dday
              beforeday = b
            }
          }
        state.pros.beforepro.push(beforeschedule[beforeday])
        state.beforeteamPicture = 'http://localhost:8080/api/v1/team/profileimg/'+state.pros.beforepro[0].teamId
        }
        console.log(state.pros.beforepro)
        console.log('dfdfdf')
        let aftermin = 32
        if (afterschedule.length >= 1) {
          for (var a = 0; a < afterschedule.length; a++) {
            if (afterschedule[a].dday <= aftermin) {
              aftermin = afterschedule[a].dday
              afterday = a
            }
          }
          console.log(afterday)
          console.log('yessssss')
          state.pros.afterpro.push(afterschedule[afterday])
          state.afterteamPicture = 'http://localhost:8080/api/v1/team/profileimg/'+state.pros.afterpro[0].teamId
        }
      })
      .catch(err => {
        console.log(err)
      })
    }


  takeSchdule()


  const takeProfile = function () {
      const token = store.getters['root/getJWTToken']
      store.dispatch('root/getProfile', token)
      .then(res => {
        state.profileinfo = res.data
        console.log(state.profileinfo)
        /*이미지 가져오기!!!!!!!!!!!! */
        if(state.profileinfo.profileImg.slice(0,4)!='http'){
          //구글에서가져오지 않고, 저장된 이미지가 있을때
          state.profileinfo.profileImg = 'http://localhost:8080/api/v1/user/profileimg/'+state.profileinfo.id;
        }else {
          //저장된 이미지 없이 default img
          state.profileinfo.profileImg = require('@/assets/images/agit_logo.png')
          console.log(state.profileinfo.profileImg)
          console.log('디폴트');
        }
        console.log("이런식으로 이미지 가져와!!!!")
        console.log(state.profileinfo.profileImg);

        if (state.profileinfo.birthDay == null) {
          state.birthdayDialogOpen = true
        }
      })
      .catch(err => {
        console.log(err)
      })
    }

    const editNickname = (nickname) => {
      state.profileinfo.nickName = nickname.nickname
    }

    const editMyPhoto = () => {
      state.profileinfo.profileImg = 'http://localhost:8080/api/v1/user/profileimg/'+state.profileinfo.id
      console.log(state.profileinfo.profileImg)
    }

    const createBirthday = (birthday) => {
      state.profileinfo.birthDay = birthday.birthday
    }

    const editColor = (jellyc) =>{
      jcolor = jellyc.jellyc
    }





  takeProfile()

    const zeroPadding = function(num, digit) {
      var zero = '';
      for(var i = 0; i < digit; i++) {
          zero += '0';
      }
      return (zero + num).slice(-digit);
  }

  const updateTime = function () {
    var cd = new Date();
    state.clock.time = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
    state.clock.date = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth()+1, 2) + '-' + zeroPadding(cd.getDate(), 2) + ' ' + week[cd.getDay()];
  }

  updateTime()

  const changeColor = function () {
    if (JSON.parse(localStorage.getItem('jelly')) != null) {
      jcolor = JSON.parse(localStorage.getItem('jelly'))
    } else {
      jcolor = 1
    }

  }


  changeColor()


  const onCloseNicknameDialog = function () {
    state.nicknameDialogOpen = false
  }

  const onCloseBirthdayDialog = function () {
    state.birthdayDialogOpen = false
  }


  // calcdate()
  const onCloseAfterDialog = function () {
    state.afterDialogOpen = false
  }

  const onCloseBeforeDialog = function () {
    state.beforeDialogOpen = false
  }

  const onCloseTermsDialog = function () {
    state.termsDialogOpen = false
  }

  const onClosePhotoDialog = function () {
    state.photoDialogOpen = false

  }

  const onCloseColorDialog = function () {
    state.colorDialogOpen = false
  }

  onBeforeMount(() => {
      checkUserState();
      takeProfile();

    })


    return { editMyPhoto, editColor, jcolor, value, router, takeProfile, state, beforeschedule, afterschedule, beforeday, afterday, takeSchdule, onCloseAfterDialog, onCloseBeforeDialog, onCloseNicknameDialog, editNickname, onCloseBirthdayDialog, createBirthday, onCloseTermsDialog, todayschedule, week, timerID, updateTime, zeroPadding, onClosePhotoDialog, onCloseColorDialog }
  }
}


</script>

<style>
.box {
  animation: bounce-in 3s 1;
  width: 150px;
  background-color: #F0FFF0;
  padding: 20px;
  border: 5px solid #20B2AA;
  text-align: center;
  font-weight: 900;
  color: #20B2AA;
  position: relative;
  transition: 0.5s;
  /* background-image: linear-gradient(to right, #84fab0 0%, #13C7A3 51%, #84fab0 100%); */
}

.sb3:before {
content: "";
width: 0px;
height: 0px;
position: absolute;
border-left: 10px solid #20B2AA;
border-right: 10px solid transparent;
border-top: 10px solid #20B2AA;
border-bottom: 10px solid transparent;
/* background: transparent; */
left: 24px;
bottom: -20px;
}

.sb4:before {
content: "";
width: 0px;
height: 0px;
position: absolute;
border-left: 10px solid transparent;
border-right: 10px solid #20B2AA;
border-top: 10px solid #20B2AA;
border-bottom: 10px solid transparent;
right: 25px;
bottom: -21px;
}

.sb5:before {
content: "";
width: 0px;
height: 0px;
position: absolute;
border-left: 10px solid transparent;
border-right: 10px solid transparent;
border-top: 10px solid #20B2AA;
border-bottom: 10px solid transparent;
left: 60px;
bottom: -25px;
}

  @keyframes bounce-in {
  from, 20%, 40%, 60%, 80%, to {
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1); }
  0% {
    opacity: 0;
    transform: scale3d(0.3, 0.3, 0.3); }
  30% {
    transform: scale3d(1.1, 1.1, 1.1); }
  60% {
    transform: scale3d(0.9, 0.9, 0.9); }
  70% {
    opacity: 1;
    transform: scale3d(1.03, 1.03, 1.03); }
  80% {
    transform: scale3d(0.97, 0.97, 0.97); }
  to {
    opacity: 1;
    transform: scale3d(1, 1, 1); } }

  /* test */
  .el-carousel__item {
  background-color: #FAFAA0;
  color: #13C7A3;
  font-size: 10px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
  z-index: -3;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #FAFAA0;
    color: #13C7A3;
  }

  .el-carousel__item:nth-child(2n + 1) {
    background-color: #FAEB78;
    color: #20B2AA;
  }

  #scape {
    width: 80vw;
    height: 100%;
    overflow-x: hidden;
    margin: 0px;
  }

  .page-wrapper {
  width: 100%;
  height: 3%;
  display: flex;
  align-items: center;
  justify-content: center;
  }

  .loader {
    position: relative;
    width: 100px;
    margin: 10px;
  }

  .loader .jelly {
    animation: jumping 1s infinite;
    cursor:pointer
  }

  .loader .body1 {
    width: 80px;
    height: 160px;
    border-radius: 40px;
    background: linear-gradient( to bottom,#32BEBE 60%, yellow);
    animation: body 1s infinite;
  }

  .loader .body2 {
    width: 80px;
    height: 160px;
    border-radius: 40px;
    background: linear-gradient( to bottom,#1E90FF, white);
    animation: body 1s infinite;
  }

  .loader .body3 {
    width: 80px;
    height: 160px;
    border-radius: 40px;
    background: linear-gradient( to bottom,pink 60%, white);
    animation: body 1s infinite;
  }

   .loader .body4 {
    width: 80px;
    height: 160px;
    border-radius: 40px;
    background: linear-gradient( to bottom,#FF5A5A 60%, white);
    animation: body 1s infinite;
  }

   .loader .body5 {
    width: 80px;
    height: 160px;
    border-radius: 40px;
    background: linear-gradient( to bottom,#FFAF0A 60%, white);
    animation: body 1s infinite;
  }

  .loader .mouth {
    position: absolute;
    top: 70px;
    left: 20px;
    width: 40px;
    height: 20px;
    border: 3px solid #2a333b;
    border-radius: 0 0 40px 40px;
    background-color: #787878;
    overflow: hidden;
    z-index: 1;
  }

  .loader .mouth:before {
    content: '';
    display: block;
    position: absolute;
    top: 10px;
    left: 0;
    width: 40px;
    height: 20px;
    border-radius: 50%;
    background-color: #FF0000;
  }

  .loader .eye {
    position: absolute;
    top: 35px;
    width: 10px;
    height: 20px;
    border-radius: 50%;
    background-color: #2a333b;
    animation: eye 6s infinite;
  }

  .loader .eye:nth-child(even) {
    left: 25px;
  }

  .loader .eye:nth-child(odd) {
    left: 45px;
  }

  .loader .shadow {
    position: absolute;
    bottom: -10px;
    z-index: -1;
    width: 100px;
    height: 20px;
    border-radius: 50%;
    background-color: rgba(42,51,59,0.2);
    animation: shadow 1s infinite;
  }

  @-moz-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-webkit-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-o-keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @keyframes jumping {
    0% {
      transform: translateY(-40px);
    }
    70% {
      transform: scale(1.02, 0.9) translateY(10px);
    }
    100% {
      transform: translateY(-40px);
    }
  }

  @-moz-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-webkit-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-o-keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @keyframes body {
    0% {
      border-radius: 50px;
    }
    70% {
      border-radius: 50px 50px 40px 40px;
    }
    100% {
      border-radius: 50px;
    }
  }

  @-moz-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-webkit-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-o-keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @keyframes eye {
    0% {
      transform: scale(1);
    }
    2% {
      transform: scale(2, 0.2);
    }
    4% {
      transform: scale(1);
    }
    6% {
      transform: scale(2, 0.2);
    }
    8% {
      transform: scale(1);
    }
  }

  @-moz-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @-webkit-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @-o-keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }

  @keyframes shadow {
    0% {
      transform: scaleX(0.9);
    }
    70% {
      transform: scaleX(1);
    }
    100% {
      transform: scaleX(0.9);
    }
  }


  .landing {
  position: relative;
  margin: auto;
  display: block;
  width: 100%;
  height: 65vh;
  background: rgb(190, 231, 232);
  background: linear-gradient(
    158deg,
    rgba(190, 231, 232, 1) 0%,
    #FAFAAA 55%

  );
}

.landing2 {
  position: relative;
  margin: auto;
  display: block;
  width: 100%;
  height: 65vh;
  background: rgb(190, 231, 232);
  background: linear-gradient(
    158deg,
    #a0a0a0 0%,
    #506EA5 55%

  );
}
.mountain-scene {
  position: relative;
  height: 75%;
  /* border-style: solid; */
  /* overflow:hidden; */
  /* margin: 0; */
}

/* Background Mountains

Triangles made using transform rotate
width = 1.41 x height(padding-bottom) */

.tri {
  width: 30%;
  padding-bottom: 21%;
  position: relative;
  overflow: hidden;
}
.tri:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  background: rgb(254, 194, 178);
  background: linear-gradient(
    180deg,
    #369F36 0%,
    #FAFAAA 55%
  );
  border-radius: 20px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.small-mountains .tri:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: -7%;
}

.small-mountains .tri:nth-child(2) {
  bottom: 0;
  position: absolute;
  left: 40%;
}

.small-mountains .tri:nth-child(3) {
  bottom: 0;
  position: absolute;
  right: -10%;
}

.tri2 {
  width: 48%;
  padding-bottom: 33%;
  position: relative;
  overflow: hidden;
}
.tri2:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  /* background: #726DA8; */
  background: rgb(254, 194, 178);
  background: linear-gradient(
    180deg,
    #FFCD28		 0%,
    #FAFAAA 55%
  );
  border-radius: 20px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.tall-mountains .tri2:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: 0%;
}

.tall-mountains .tri2:nth-child(2) {
  bottom: 0;
  position: absolute;
  left: 14%;
}

.tall-mountains .tri2:nth-child(3) {
  bottom: 0;
  position: absolute;
  left: 50%;
}

/* Foreground mountains */

.tri3 {
  width: 19%;
  padding-bottom: 13%;
  position: relative;
  overflow: hidden;
}

.tri3:before {
  content: "";
  position: absolute;

  width: 100%;
  height: 104%;
  background: rgb(233, 243, 244);
  background: linear-gradient(
    180deg,
    rgba(233, 243, 244, 1) 0%,
    rgba(190, 231, 232, 1) 72%
  );
  border-radius: 18px;

  -webkit-transform-origin: 0 100%;
  -ms-transform-origin: 0 100%;
  transform-origin: 0 100%;

  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.tiny-mountains .tri3:nth-child(1) {
  bottom: 0;
  position: absolute;
  left: 9%;
}
.tiny-mountains .tri3:nth-child(2) {
  bottom: -3%;
  position: absolute;
  left: 31%;
}

.tiny-mountains .tri3:nth-child(3) {
  bottom: -7%;
  position: absolute;
  left: 42%;
}

.tiny-mountains .tri3:nth-child(4) {
  bottom: 0;
  position: absolute;
  left: 72%;
}

.tiny-mountains .tri3:nth-child(5) {
  bottom: -5%;
  position: absolute;
  left: 62%;
}
.ground {
  position: relative;
  height: 25%;
  background: rgb(255, 246, 202);
  background: linear-gradient(
    180deg,
    rgba(255, 246, 202, 1) 0%,
    rgba(255, 229, 164, 1) 72%
  );
}

#clouds {
  padding: 4vw 0;
}

/* base of cloud */
.cloud {
  height: 4vw;
  width: 13vw;
  background: white;
  border-radius: 40vw;
  position: relative;
}

/* smaller circle within cloud */
.cloud:before {
  content: "";
  position: absolute;
  top: -2vw;
  height: 6vw;
  width: 6vw;
  left: 2vw;
  border-radius: 50%;
  background: white;
}

/* larger circle within cloud */
.cloud:after {
  content: "";
  position: absolute;
  height: 6vw;
  width: 6vw;
  top: -3vw;
  left: 5vw;
  border-radius: 50%;
  background: white;
}

#clouds .cloud:nth-child(1) {
  opacity: 0.7;
  animation: moveclouds 17s linear infinite;
  -webkit-animation: moveclouds 17s linear infinite;
  -moz-animation: moveclouds 17s linear infinite;
  -o-animation: moveclouds 17s linear infinite;
}
#clouds .cloud:nth-child(2) {
  left: 75%;
  opacity: 0.5;
  animation: moveclouds 22s linear infinite;
  -webkit-animation: moveclouds 22s linear infinite;
  -moz-animation: moveclouds 22s linear infinite;
  -o-animation: moveclouds 22s linear infinite;
}
#clouds .cloud:nth-child(3) {
  left: 50%;
  z-index: 1;
  animation: moveclouds 15s linear infinite;
  -webkit-animation: moveclouds 15s linear infinite;
  -moz-animation: moveclouds 15s linear infinite;
  -o-animation: moveclouds 15s linear infinite;
}
#clouds .cloud:nth-child(4) {
  left: 90%;
  opacity: 0.7;
  transform: scale(0.6);
  -webkit-transform: scale(0.6);
  -moz-transform: scale(0.6);
  -o-transform: scale(0.6);
  animation: moveclouds 16s linear infinite;
  -webkit-animation: moveclouds 16s linear infinite;
  -moz-animation: moveclouds 16s linear infinite;
  -o-animation: moveclouds 16s linear infinite;
}
#clouds .cloud:nth-child(5) {
  left: 100%;
  opacity: 0.4;
  z-index: 1;

  transform: scale(0.8);
  -webkit-transform: scale(0.8);
  -moz-transform: scale(0.8);
  -o-transform: scale(0.8);

  animation: moveclouds 22s linear infinite;
  -webkit-animation: moveclouds 22s linear infinite;
  -moz-animation: moveclouds 22s linear infinite;
  -o-animation: moveclouds 22s linear infinite;
}
@keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}

@-webkit-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}
@-moz-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}
@-o-keyframes moveclouds {
  0% {
    margin-left: 1000px;
  }
  100% {
    margin-left: -1000px;
  }
}

@import url('https://fonts.googleapis.com/css?family=Roboto:400,500,700');



a {
  text-decoration: none;
  color: white;
}

/* .container {
  display: flex;
  justify-content: center;
  align-content: flex-start;
  /* position:relative; */
  /* height: 88vh; */
  /* width: 100vw; */
/* } */


.iphone {
  background-color: #F4F4FB;
  height:81.3vh;
  width: 78vh;
  /* overflow: hidden; */
  position: relative;
  z-index: 0;
  transform:scale(0.8);
  transform-origin: top center;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 30%;
}

.order-summary {
  margin-left: 1.5rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
}

.order-summary > div {
  margin: 6px;
}

.order-status {
  color: #338A9A;
  font-size: 1.5rem;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.order-date {
  font-size: 2rem;
  font-weight: 700;
}

.order-day {
  color: #338A9A;
  font-size: 1.5rem;
  font-weight: 300;
  letter-spacing: 0.5px;
}

.hero-img-container {
  width: 100%;
  height: 36vh;
   display: flex;
    justify-content: center;
    align-items: center;
  position: relative;
  padding-bottom: 20px;
}

.hero-img {
  width: 80% !important;
  height: 80% !important;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

/* .img-photo {
  width: 90px;
  transform: rotate(-35deg)
} */


p{
	 margin: 0;
	 padding: 0;
}
 #clock {
	 font-family: 'Share Tech Mono', monospace;
	 color: #fff;
	 text-align: center;
	 position: absolute;
	 left: 50%;
	 top: 50%;
	 transform: translate(-50%, -50%);
	 color:#20B2AA	;
	 text-shadow: 0 0 20px rgba(10, 175, 230, 1), 0 0 20px rgba(10, 175, 230, 0);
}
 #clock .time {
	 letter-spacing: 0.05em;
	 font-size: 80px;
	 padding: 5px 0;
}
 #clock .date {
	 letter-spacing: 0.1em;
	 font-size: 24px;
}
 #clock .text {
	 letter-spacing: 0.1em;
	 font-size: 12px;
	 padding: 20px 0 0;
}

</style>
