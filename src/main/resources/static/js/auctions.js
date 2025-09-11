function startCountdowns() {
  const countdowns = document.querySelectorAll(".countdown");
  countdowns.forEach(span => {
    const endTime = new Date(span.getAttribute("data-end"));
    const interval = setInterval(() => {
      const now = new Date();
      const diff = endTime - now;
      if (diff <= 0) {
        span.textContent = "Auction ended";
        clearInterval(interval);
      } else {
        const mins = Math.floor(diff / 60000);
        const secs = Math.floor((diff % 60000) / 1000);
        span.textContent = `${mins}m ${secs}s`;
      }
    }, 1000);
  });
}

document.addEventListener("DOMContentLoaded", () => {
  fetch("/api/auctions/active")
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById("auction-list");
      data.forEach(item => {
        const card = document.createElement("div");
        card.className = "auction-card";
        card.innerHTML = `
          <h3>${item.title}</h3>
          <p>${item.description}</p>
          <p>Current Bid: ₹${item.currentBid}</p>
          <p>Ends in: <span class="countdown" data-end="${item.endTime}"></span></p>
        `;
        container.appendChild(card);
      });
      startCountdowns();
    });
});
