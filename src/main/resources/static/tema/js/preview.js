// preview.js
document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById('imagen');
    const preview = document.getElementById('preview');

    if (input) {
        input.addEventListener('change', function (e) {
            const file = e.target.files[0];

            if (file && file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function (event) {
                    preview.src = event.target.result;
                    preview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            } else {
                preview.style.display = 'none';
                preview.src = '';
            }
        });
    }
});
