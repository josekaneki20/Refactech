<script>
  const toggleBtn = document.getElementById('toggleSidebar');
  const sidebar = document.querySelector('.sidebar');
  const productos = document.querySelector('.productos');

  toggleBtn.addEventListener('click', () => {
    sidebar.classList.toggle('hidden');
    productos.classList.toggle('shifted');
  });
</script>
