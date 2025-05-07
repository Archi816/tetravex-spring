const otherLink = document.getElementById('other-link');
const otherMenu = document.getElementById('other-menu');

otherLink.addEventListener('click', function(event) {
    event.stopPropagation(); // Чтобы клик не всплывал

    const isVisible = otherMenu.style.display === 'flex';

    if (isVisible) {
        otherMenu.style.display = 'none';
        otherLink.style.display = 'inline';
    } else {
        otherMenu.style.display = 'flex';
        otherLink.style.display = 'none';
    }
});

document.addEventListener('click', function() {
    otherMenu.style.display = 'none';
    otherLink.style.display = 'inline';
});

otherMenu.addEventListener('click', function(event) {
    event.stopPropagation();
});
