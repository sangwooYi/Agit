<template>
  <div>
    <!-- <p v-for="(item, index) in eventList" :key="item" :class="[uploadDate === item.endDate ? 'event-item deadline-item' : 'event-item normal-item']" style="text-align: center;" @click="onCellClick(item)"> -->
    <p v-for="(item, index) in eventList" :key="item" :class="{'event-item end-item': item.dday < 0, 'event-item deadline-item': item.dday >= 0 && item.dday <= 3, 'event-item normal-item': item.dday > 3}" style="text-align: center;" @click="onCellClick(item)">
      <p>
        {{ item.eventTitle }}
      </p>
      <p v-if="item.dday >= 0 && item.dday <= 3" class="ms-2">
        <img :src="require(`@/assets/images/deadline.png`)" alt="" height="30">
      </p>
      <p v-if="item.dday < 0" class="ms-2">
        <img :src="require(`@/assets/images/thumbs-up.png`)" alt="" height="30">
      </p>
    </p>
  </div>
  <event-view-dialog
    :open="state.eventViewOpen"
    :info="state.event"
    @closeEventView="onCloseEventView"/>

</template>

<script>
import { reactive } from 'vue'
import EventViewDialog from './dialog/event-view-dialog.vue'

export default {
  name: 'event',
  components: {
    EventViewDialog,
  },
  props: {
    eventList: {
      type: Array
    },
    uploadDate: {
      type: String
    }
  },
  setup (props) {
    const state = reactive({
      eventViewOpen: false,
      event: {
        'dday': '',
        'endDate': '1970-01-01',
        'eventContent': '',
        'startDate': '1970-01-01',
        'teamId': '',
        'teamName': ''
      }
    })

    const onCellClick = function (val) {
      state.event = val
      state.eventViewOpen = true
    }

    const onCloseEventView = function () {
      state.eventViewOpen = false
    }

    const getToday = function () {
      const today = new Date()
      const year = today.getFullYear()
      const month = ("0" + (1 + today.getMonth())).slice(-2);
      const day = ("0" + today.getDate()).slice(-2);

      return `${year}-${month}-${day}`
    }

    const dayDiff = function(sd, ed) {
      var sdt = new Date(sd);
      var edt = new Date(ed);
      var dateDiff = Math.ceil((edt.getTime() - sdt.getTime()) / (1000*3600*24));
      return dateDiff
    }

    return { state, onCellClick, onCloseEventView, getToday, dayDiff }
  }
}
</script>

<style>
.event-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: calc(100% - 20px);
  height: 50px;
  margin: 10px;
  text-align: center;
  border: 1px solid #d1d1d1;
  border-radius: 4px;
  /* background-color: #fff9e4; */
  color: var(--el-text-color-regular);
}

.event-item:active {
  opacity: 0.5;
}

.normal-item {
  background-color: #fff9e4;
}

.normal-item:hover {
  background-color: #e3ddca;
  transition: 0.1s;
}

.deadline-item {
  background-color: #ff8181;
  color: white;
  border: 1px solid #ff7482;
}

.deadline-item:hover {
  background-color: #cb6666;
  transition: 0.1s;
}

.end-item {
  background-color: #31cd31;
  color: white;
  border: 1px solid #28ad28;
}

.end-item:hover {
  background-color: #1d871d;
  transition: 0.1s;
}

</style>
