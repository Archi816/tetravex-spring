let selectedTiles = [];

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('.tile').forEach(tile => {
        tile.addEventListener('click', () => {
            tile.classList.toggle('selected');

            const x = tile.dataset.x;
            const y = tile.dataset.y;

            selectedTiles.push({x, y});

            // Если выбраны две плитки, отправляем запрос на обмен
            if (selectedTiles.length === 2) {
                const [first, second] = selectedTiles;

                selectedTiles = [];

                const url = `/tetravex?x1=${first.x}&y1=${first.y}&x2=${second.x}&y2=${second.y}`;
                window.location.href = url;
            }
        });
    });
});

let startTimeInput = document.getElementById('startTime');
let startTime = startTimeInput ? parseInt(startTimeInput.value) : Date.now();

if (!startTime || isNaN(startTime)) {
    startTime = Date.now();
}

function updateTimer() {
    const now = Date.now();
    const elapsed = Math.floor((now - startTime) / 1000);
    const minutes = String(Math.floor(elapsed / 60)).padStart(2, '0');
    const seconds = String(elapsed % 60).padStart(2, '0');
    const timer = document.getElementById('timer');
    if (timer) {
        timer.textContent = `${minutes}:${seconds}`;
    }
}

setInterval(updateTimer, 1000);

function toggleForm() {
    const form = document.getElementById("start-game-form");

    const computedStyle = window.getComputedStyle(form);
    const currentDisplay = computedStyle.display;

    form.style.display = (currentDisplay === "none" || currentDisplay === "") ? "block" : "none";
}

function hideForm() {
    const form = document.getElementById("start-game-form");
    form.style.display = "none";
}

document.addEventListener('DOMContentLoaded', function() {
    const newGameButton = document.getElementById("new-game-button");
    if (newGameButton) {
        newGameButton.addEventListener("click", toggleForm);
    }

    const startButton = document.getElementById("start-button");
    if (startButton) {
        startButton.addEventListener("click", hideForm);
    }
});





