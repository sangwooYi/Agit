<template>
  <el-dialog
    custom-class="temrs-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        이용약관
      </span>
    </template>

    <!-- content -->
    <div class="d-flex flex-column justify-content-around" style="height: 400px;">
      <div style="overflow:scroll;">
        <h2 class="fw-bold">
      <span style="color: #20B2AA">아지트  </span>
      <span style="color: #FFDC37">이용약관 </span>
      <!-- 이미지 못 불러오는데 확인 필요 -->
      </h2>
    <div class="d-flex flex-column align-items-start py-2">
      <div class="text-start badge bg-primary text-wrap my-2">
      제1조. 목적
      </div>

      <div class="text-start" style="font-size:13px;">
        이 이용약관(이하 ‘약관’)은 아지트(이하 ‘회사’)가 제공하는 아지트 및 아지트 관련 제반 서비스(이하 ‘서비스’)를 이용고객(이하 ‘회원’)이 이용함에 있어 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 구체적으로 규정함을 목적으로 합니다.
      </div>

      <div class="text-start badge bg-primary text-wrap my-2" >
      제2조. 용어의 정의
      </div>

      <div class="text-start">
        <p style="margin-bottom: 6px; font-size:13px">이 약관에서 사용하는 용어의 정의는 다음과 같습니다.</p>
        <p class="my-0" style="font-size:13px">“서비스”라 함은 구현되는 단말기와 상관없이 “회원” 혹은 웹사이트 방문자가 이용할 수 있는 아지트 서비스를 의미합니다.</p>
        <p class="my-0" style="font-size:13px">“회원”이란 “회사”의 서비스에 접속하여 본 약관에 따라 회사와 이용계약을 체결하고 “회사”가 제공하는 “서비스”를 이용하는 고객을 말합니다.</p>
        <p class="my-0" style="font-size:13px">“이용계약”이란 이 약관을 포함하여 서비스 이용과 관련하여 회사와 회원 간에 체결하는 모든 계약을 말합니다.</p>
        <p class="my-0" style="font-size:13px">“아이디(ID)”라 함은 회원의 식별 및 서비스 이용을 위하여 회원의 신청에 따라 회사가 회원 별로 부여하는 고유한 문자와 숫자의 조합을 말합니다.</p>
        <p class="my-0" style="font-size:13px">“이메일”은 회원가입이나 로그인 등에 사용되는 단일한 이메일 주소를 말합니다.</p>
        <p class="my-0" style="font-size:13px">“비밀번호(Password)”란 아이디(ID)로 식별되는 회원의 본인 여부를 검증하기 위하여 회원이 설정하여 회사에 등록한 고유의 문자와 숫자의 조합을 말합니다.</p>
        <p class="my-0" style="font-size:13px">“게시물”이라 함은 “회원”이 “서비스”를 이용함에 있어 “서비스” 상에 게시한 글, 사진, 동영상 및 각종 파일과 링크를 의미합니다.</p>
        <p class="my-0" style="font-size:13px">이 약관에서 사용하는 용어 중 제1항에서 정하지 아니한 것은 관계 법령 및 서비스 별 안내에서 정하는 바에 따르며, 그 외에는 일반 관례에 따릅니다.</p>
      </div>

      <div class="text-start badge bg-primary text-wrap my-2">
      제3조. 약관 효력 및 변경
      </div>

      <div class="text-start">
        <p class="my-0" style="font-size:13px">"회사”는 이 약관의 내용을 "회원"이 쉽게 알 수 있도록 서비스 초기 화면 또는 링크로 연결된 화면에 게시합니다.</p>
        <p class="my-0" style="font-size:13px">“회사”는 “약관의규제에관한법률”, “정보통신망이용촉진및정보보호등에관한법률(이하 “정보통신망법”)” 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.</p>
        <p class="my-0" style="font-size:13px">"회사"가 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 제1항의 방식에 따라 그 개정약관의 적용일자 7일 전부터 적용일자 전일까지 공지합니다. 다만, 회원에게 불리한 약관의 개정의 경우에는 최소 30일 전에 공지 하고, 서비스 내 전자우편, 전자쪽지, 로그인시 동의창 등의 전자적 수단을 통해 개별적으로 명확히 통지하도록 합니다.</p>
        <p class="my-0" style="font-size:13px">“회사”가 전 항에 따라 개정약관을 공지 또는 통지하면서 회원에게 위 기간 내에 의사표시를 하지 않으면 의사표시가 표명된 것으로 본다는 뜻을 명확하게 공지 또는 통지 하였음에도 회원이 명시적으로 거부의 의사표시를 하지 아니한 경우 회원이 개정약관에 동의한 것으로 봅니다.</p>
        <p class="my-0" style="font-size:13px">“회원”이 개정약관의 적용에 동의하지 않는 경우 “회사”는 개정 약관의 내용을 적용할 수 없으며, 이 경우 “회사” 또는 “회원”은 이용계약을 해지할 수 있습니다.</p>
        <p class="my-0" style="font-size:13px"></p>
      </div>

      <div class="text-start badge bg-primary text-wrap my-2">
      제4조 . 약관 외 준칙
      </div>

      <div class="text-start">
        <p class="my-0" style="font-size:13px">회사는 필요한 경우 서비스 내의 개별항목에 대하여 개별약관 또는 운영원칙(이하 ‘서비스 별 안내’라 합니다.)를 정할 수 있으며, 회원은 각 서비스 별 안내에 대해 회원가입과 동시에 동의한 것으로 간주합니다. 본 약관과 서비스 별 안내의 내용이 상충되는 경우에는 서비스 별 안내의 내용을 우선하여 적용합니다.</p>
      </div>


      <div class="text-start badge bg-primary text-wrap my-2">
      제5조. 이용계약 체결
      </div>

      <div class="text-start">
        <p class="my-0" style="font-size:13px">이용계약은 “회원”이 되고자 하는 자(이하 “가입신청자”)가 약관의 내용에 대하여 동의를 한 다음 회원가입신청을 하고 “회사”가 이러한 신청에 대하여 승낙함으로써 체결됩니다.</p>
        <p class="my-0" style="font-size:13px">“회사”는 “가입신청자”의 신청에 대하여 “서비스” 이용을 승낙함을 원칙으로 합니다. 다만, “회사”는 다음 각 호에 해당하는 신청에 대하여는 승낙을 하지 않거나 사후에 이용계약을 해지할 수 있습니다.</p>
        <p class="my-0" style="font-size:13px">가입 신청자가 이 약관에 의하여 이전에 회원자격을 상실한 적이 있는 경우, 단 “회사”의 회원 재가입 승낙을 얻은 경우에는 예외로 함.</p>
        <p class="my-0" style="font-size:13px">실명이 아니거나 타인의 명의를 이용한 경우</p>
        <p class="my-0" style="font-size:13px">허위의 정보를 기재하거나, “회사”가 제시하는 내용을 기재하지 않은 경우</p>
        <p class="my-0" style="font-size:13px">14세 미만 아동이 법정대리인(부모 등)의 동의를 얻지 아니한 경우</p>
        <p class="my-0" style="font-size:13px">"서비스"의 위상이나 명예에 부정적인 영향을 줄 수 있는 경우</p>
        <p class="my-0" style="font-size:13px">부정한 행위나 과정을 통해 공공질서 및 미풍양속에 저해되는 활동을 하는 경우</p>
        <p class="my-0" style="font-size:13px">타인의 명예를 손상시키거나 불이익을 주는 행위를 한 경우</p>
        <p class="my-0" style="font-size:13px">영리를 추구할 목적으로 "서비스"를 악용하는 경우</p>
        <p class="my-0" style="font-size:13px">"서비스"의 운영을 고의로 방해한 경우</p>
        <p class="my-0" style="font-size:13px">이용자의 귀책사유로 인하여 승인이 불가능하거나 기타 규정한 제반 사항을 위반하며 신청하는 경우</p>
      </div>

      <div class="text-start badge bg-primary text-wrap my-2">
      제6조. 회원정보 변경
      </div>

      <div class="text-start">
        <p class="my-0" style="font-size:13px">“회원”은 개인정보관리화면을 통하여 언제든지 본인의 개인정보를 열람하고 수정할 수 있습니다. 다만, 서비스 관리를 위해 필요한 아이디등은 수정이 불가능합니다.</p>
        <p class="my-0" style="font-size:13px">“회원”은 회원가입신청 시 기재한 사항이 변경되었을 경우 온라인으로 수정을 하거나 전자우편 기타 방법으로 “회사”에 대하여 그 변경사항을 알려야 합니다.</p>
      </div>

      <div class="text-start badge bg-primary text-wrap my-2">
      제7조. 개인정보보호 의무
      </div>

      <div class="text-start">
        <p class="my-0" style="font-size:13px">회사는 회원에게 서비스를 제공하는 과정에서 정보통신망 이용촉진 및 정보보호 등에 관한 법률, 개인정보보호법 등 관련법령이 규정한 절차와 방법에 따라 회원의 개인정보를 보호하기 위해 노력합니다. 개인정보의 보호 및 이용에 대해서는 관련법령 및 회사의 개인정보처리방침이 적용됩니다.</p>
        <p class="my-0" style="font-size:13px">회사는 회원의 귀책사유로 인하여 노출된 회원의 회원정보를 포함한 모든 정보에 대해서 일체의 책임을 지지 않습니다.</p>
        <p class="my-0" style="font-size:13px">“회원”의 “아이디”와 “비밀번호” 그리고 “이메일”에 관한 관리책임은 “회원”에게 있으며, 이를 제3자가 이용하도록 하여서는 안 됩니다.</p>
        <p class="my-0" style="font-size:13px">“회사”는 “회원”의 “아이디”가 개인정보 유출 우려가 있거나, 반사회적 또는 미풍양속에 어긋나거나 “회사” 및 “회사”의 운영자로 오인한 우려가 있는 경우, 해당 “아이디”의 이용을 제한할 수 있습니다.</p>
        <p class="my-0" style="font-size:13px">“회원”은 “아이디” 및 “비밀번호”가 도용되거나 제3자가 사용하고 있음을 인지한 경우에는 이를 즉시 “회사”에 통지하고 “회사”의 안내에 따라야 합니다.</p>
      </div>
    </div>
    <div style="margin-top: 20px" class="d-flex justify-content-between">
      <div>
        <div class="fw-bold">[부칙]</div>
        <div class="fw-bold">본 약관은 2022년 1월 12일부터 적용됩니다.</div>
      </div>
      <div>
        <el-button type="danger" @click="handleClose">닫기</el-button>
      </div>
    </div>
      </div>
    </div>
    <!-- footer -->
  </el-dialog>

</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'terms-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
  },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
    })

    const handleClose = function () {
      emit('closeTermsDialog')
    }

    return { store, router, handleClose, state }

  }
}
</script>

<style>
.terms-dialog {
  width: 1000px !important;
  height: 1200px !important;
}
</style>
