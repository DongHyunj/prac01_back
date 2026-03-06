<script setup>
import axios from "axios";

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

    await axios.put('http://localhost:8083/push/sub', subscription);
  }
}
</script>

<template>
  <button @click="subscribePush">알림 구독</button>
</template>

<style scoped>

</style>
