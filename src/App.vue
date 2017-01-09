<template>
  <div id="app">
    <a href="#" class="btn" @click="sacarFoto()">Sacar Foto</a>
    <img class="imagen" :src="imgsrc">
  </div>
</template>

<script>
import Vue from 'vue'

export default {
  methods: {
    sacarFoto: function () {
      Vue.cordova.camera.getPicture((imageURI) => {
        this.imgsrc = imageURI
        console.log(this.imgsrc)
      }, (message) => {
        this.imgsrc = ''
      }, {
        quality: 50,
        destinationType: Vue.cordova.camera.DestinationType.FILE_URI
      })
    }
  },
  data: function () {
    return {
      imgsrc: '',
      cordova: Vue.cordova,
      plugins: [
        'cordova-plugin-camera',
        'cordova-plugin-device'
      ]
    }
  }
}
</script>

<style>
html {
  height: 100%;
}

body {
  height: 100%;
}

#app {
  color: #2c3e50;
  margin: 40px auto;
  max-width: 600px;
  font-family: Source Sans Pro, Helvetica, sans-serif;
  text-align: center;
}

.imagen {
  max-width: 100%;
}

.btn {
  display: block;
  background: #3498db;
  background-image: -webkit-linear-gradient(top, #3498db, #2980b9);
  background-image: -moz-linear-gradient(top, #3498db, #2980b9);
  background-image: -ms-linear-gradient(top, #3498db, #2980b9);
  background-image: -o-linear-gradient(top, #3498db, #2980b9);
  background-image: linear-gradient(to bottom, #3498db, #2980b9);
  -webkit-border-radius: 11;
  -moz-border-radius: 11;
  border-radius: 11px;
  color: #ffffff;
  font-size: 20px;
  padding: 10px 20px 10px 20px;
  text-decoration: none;
  margin-bottom: 10px;
}

.btn:hover {
  text-decoration: none;
}
</style>
