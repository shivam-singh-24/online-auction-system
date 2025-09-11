document.getElementById("registerForm").addEventListener("submit", function(e) {
  e.preventDefault();

  const formData = new FormData(this);
  const user = {
    username: formData.get("username"),
    email: formData.get("email"),
    password: formData.get("password"),
    role: formData.get("role")
  };

  fetch("/api/users/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user)
  })
  .then(res => res.json())
  .then(data => {
    alert("Registration successful!");
    window.location.href = "/html/login.html";
  })
  .catch(err => alert("Error: " + err));
});
