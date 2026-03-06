<script setup>
import axios from "axios";
import {ref} from 'vue';

const message = ref('');
const socket = ref(null);
const user = ref({
  email: '',
  password: ''
})

const connectWebSocketServer = () => {
  const ws = new WebSocket("ws://localhost:5173/ws")
  socket.value = ws;

  ws.onmessage = (message) => {
    console.log(message.data)
  }
}

const sendMessage = () => {
  socket.value.send(JSON.stringify(message.value))
}

const login = async () => {
  await  axios.post("http://localhost:5173/api/user/login", user.value)
}

const subscribePush = async () => {
  const permission = await Notification.requestPermission()
  if(permission !== 'granted') {
    console.log("권한없음")
    return
  }
  await navigator.serviceWorker.register('/service-worker.js')

  const VAPID_PUBLIC_KEY= 'BPaZq-g4ccJZP5w6rdCs8ZBle2U0z4WLztW-ezkCh-fg3auvG1-kk-PYznP80COID18IzxQlKytBooY2ge40Ftg'

  const registration = await navigator.serviceWorker.ready;
  let subscription = await registration.pushManager.getSubscription()

  if(!subscription) {
    subscription = await registration.pushManager.subscribe({
      userVisibleOnly: true,
      applicationServerKey: VAPID_PUBLIC_KEY
    })

    console.log(JSON.stringify(subscription))

    await axios.post('http://localhost:8080/push/sub', subscription);
  }
}
</script>

<template>
  <button @click="connectWebSocketServer">웹 소켓 연결</button>
  <input name="message" v-model="message"/>
  <button @click="sendMessage">메시지 전송</button>

  <hr>

  <button @click="subscribePush">알림 구독</button>

  <hr>
  <input name="email" v-model="user.email"/>
  <input name="password" v-model="user.password"/>
  <button @click="login">로그인</button>

</template>

<style scoped>

</style>
