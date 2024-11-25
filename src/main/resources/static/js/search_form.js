function updateMinCheckOutDate() {
    const checkInDate = document.getElementById("checkInDate");
    const checkOutDate = document.getElementById("checkOutDate");

    if (checkInDate.value >= checkOutDate.value) {
        const nextDay = new Date(checkInDate.value);
        nextDay.setDate(nextDay.getDate() + 1); // Устанавливаем дату на день позже

        const formattedNextDay = nextDay.toISOString().split("T")[0]; // Формат YYYY-MM-DD
        checkOutDate.value = formattedNextDay; // Также устанавливаем выбранное значение
    }
}


function updateMaxCheckInDate() {
    const checkOutDate = document.getElementById("checkOutDate");
    const checkInDate = document.getElementById("checkInDate");

    if (checkOutDate.value <= checkInDate.value) {
        const prevDay = new Date(checkOutDate.value);
        prevDay.setDate(prevDay.getDate() - 1); // Устанавливаем дату на день раньше

        const formattedPrevDay = prevDay.toISOString().split("T")[0]; // Формат YYYY-MM-DD
        checkInDate.value = formattedPrevDay; // Также устанавливаем выбранное значение
    }
}