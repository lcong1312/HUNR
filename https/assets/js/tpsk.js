    // Kiểm tra localStorage để ẩn trong 24h
    const popupTBSK = document.getElementById("popup-tb-sk");
    const closePopupTBSK = document.getElementById("closePopup-tb-sk");
    const lastClosedTBSK = localStorage.getItem("popupClosedTimeTB-SK");
    const nowTBSK = Date.now();
    const hours24TBSK = 12 * 60 * 60 * 1000;

    if (!lastClosedTBSK || nowTBSK - lastClosedTBSK > hours24TBSK) {
        setTimeout(() => {
            popupTBSK.classList.add("show-tb-sk");
        }, 800); // hiển thị sau 0.8s
    }

    closePopupTBSK.addEventListener("click", () => {
        popupTBSK.classList.remove("show-tb-sk");
        localStorage.setItem("popupClosedTimeTB-SK", Date.now());
    });