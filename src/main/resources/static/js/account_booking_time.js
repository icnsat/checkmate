document.addEventListener("DOMContentLoaded", function () {
        const dateElements = document.querySelectorAll(".booking-date");
        dateElements.forEach(el => {
            const isoDate = el.textContent.trim();
            if (isoDate) {
                // Парсим дату из строки
                const dateObj = new Date(isoDate);
                if (!isNaN(dateObj)) {
                    // Форматируем дату: YYYY-MM-DD, HH:mm:ss
                    const formattedDate = dateObj.getFullYear() + "-" +
                        String(dateObj.getMonth() + 1).padStart(2, '0') + "-" +
                        String(dateObj.getDate()).padStart(2, '0') + ", " +
                        String(dateObj.getHours()).padStart(2, '0') + ":" +
                        String(dateObj.getMinutes()).padStart(2, '0') + ":" +
                        String(dateObj.getSeconds()).padStart(2, '0');
                    el.textContent = formattedDate;
                }
            }
        });
    });