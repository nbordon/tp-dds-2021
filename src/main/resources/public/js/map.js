const tilesProvider = 'https://{s}.tile.openstreetmap.de/{z}/{x}/{y}.png'

let myMap = L.map('myMap').setView([51.505, -0.09], 13)

L.tileLayer(tilesProvider,{
    maxZoom: 18
}).addTo(myMap)

let marker = L.marker([51.505, -0.09]).addTo(myMap)

myMap.doubleClickZoom.disable()
myMap.on('dblclick', e => {
    let latLng = myMap.mouseEventToLatLng(e.originalEvent)
    myMap.removeLayer(marker)
    marker = L.marker([latLng.lat, latLng.lng]).addTo(myMap)
    let lat = document.getElementById("latitud")
    let lng = document.getElementById("longitud")
    lat.value = latLng.lat
    lng.value = latLng.lng
})

navigator.geolocation.getCurrentPosition(
    (pos) =>{
        const { coords } = pos
        myMap.setView([coords.latitude, coords.longitude ],13)
        myMap.removeLayer(marker)
        marker = L.marker([coords.latitude, coords.longitude]).addTo(myMap)
        let lat = document.getElementById("latitud")
        let lng = document.getElementById("longitud")
        lat.value = coords.latitude
        lng.value = coords.longitude
    },
    (err) =>{
        console.log(err)
    },
    {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
    }
)