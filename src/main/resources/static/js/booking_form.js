document.addEventListener("DOMContentLoaded", function () {
        const checkInDate = new Date(document.getElementById("checkInDate").innerHTML);
        const checkOutDate = new Date(document.getElementById("checkOutDate").innerHTML);
        const roomPrice = document.getElementById("roomPrice").innerHTML;//.split(' ')[0];
        console.log(roomPrice);

        const diff = (checkOutDate - checkInDate) / (1000 * 60 * 60 * 24);
        const calcPrice = parseFloat(parseFloat(roomPrice) * diff);
        console.log(calcPrice);
        const fullPrice = document.getElementById("fullPrice");
        fullPrice.innerHTML = calcPrice  + ' руб.';


        const totalPrice = document.getElementById("totalPrice");
        totalPrice.value = parseFloat(calcPrice);
});