<template>
  <!-- <p>{{ info }}</p> -->
  <el-dialog
    custom-class="color-dialog"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="30%"
  >

    <!-- header -->
    <template #title>
      <!-- <span style="font-size: 20px; font-weight: bolder;"> -->
      <span>
        색깔 변경
      </span>
    </template>

    <!-- content -->
    <div class="d-flex justify-content-between">
      <el-select v-model="value" clearable placeholder="Select">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
          id="item.value"
        >
        </el-option>
      </el-select>
      <div>
        <el-button @click="editColor">색깔 등록</el-button>
      </div>
    </div>
    <!-- footer -->
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

export default {
  name: 'color-dialog',
  emits: ['edit-color'],
  // emits: ['create-birthday'],
  props: {
    open: {
      type: Boolean,
      default: false
    },
    // info: {
    //   type: Object,
    //   required: true,
    // }
  },
  setup(props, { emit }) {
    // const store = useStore()
    const router = useRouter()
    const state = reactive({
      dialogVisible: computed(() => props.open),
    })
    const value = ref('')

    const handleClose = function () {
      emit('closeColorDialog')
    }

    const options = [
  {
    value: '1',
    label: '민트(기본)',
  },
  {
    value: '2',
    label: '블루',
  },
  {
    value: '3',
    label: '핑크',
  },
  {
    value: '4',
    label: '레드',
  },
  {
    value: '5',
    label: '오렌지',
  },
]

  const editColor = function () {
      if ( ref(value).value != '') {
        // emit('edit-color', {
        //   color: ref(value),
        // })
        emit('edit-color', {
          jellyc: ref(value).value,
        })
        console.log('색상 확인')
        console.log(ref(value).value)
        // console.log(props.info.cdate)
        localStorage.setItem('jelly', JSON.stringify(ref(value).value));
        setTimeout(() => {
                swal({
                  title: "색깔 등록",
                  text: "색깔이 성공적으로 등록되었습니다..",
                  icon: "success",
                  button: "확인",
                });
              }, 500)

              router.go(router.currentRoute)
        handleClose()

      } else {
        setTimeout(() => {
                swal({
                  title: "색깔 등록 실패",
                  text: "색깔을 선택해주세요",
                  icon: "error",
                  button: "확인",
                });
              }, 500)

              // router.go(router.currentRoute)
      }
    }

    return { state, handleClose, value,  options, editColor }
  }

}
</script>

<style>
  .color-dialog {
    width: 300px !important;
  }
</style>
