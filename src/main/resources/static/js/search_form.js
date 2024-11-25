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


function fetchCities() {
    const query = document.getElementById("city").value;
    const suggestions = document.getElementById("citySuggestions");

    if (query.length < 2) {
        suggestions.style.display = "none";
        suggestions.innerHTML = "";
        return;
    }

    fetch(`/api/cities?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(cities => {
            suggestions.innerHTML = "";
            cities.forEach(city => {
                const li = document.createElement("li");
                li.textContent = `${city.name}, ${city.country}`;
                li.className = 'list-group-item list-group-item-action';
                li.onclick = () => {
                    document.getElementById("city").value = `${city.name}, ${city.country}`;
                    document.getElementById("cityId").value = city.id;
                    suggestions.style.display = "none";
                };
                suggestions.appendChild(li);
            });
            suggestions.style.display = "block";
        })
        .catch(error => {
            console.error("Ошибка при получении списка городов:", error);
        });
}
