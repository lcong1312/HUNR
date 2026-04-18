(function(){
    const KEY = 'topup_notice_hidden_until';
    const notice = document.getElementById('topupNotice');
    const closeBtn = document.getElementById('closeBtn');
    const dontShow = document.getElementById('dontShow');

    function shouldShow(){
      try {
        const val = localStorage.getItem(KEY);
        if(!val) return true;
        const until = Number(val);
        return Date.now() > until;
      } catch(e){ return true }
    }

    function hide(forMs){
      notice.classList.remove('show-tb');
      if(forMs){
        try { localStorage.setItem(KEY, String(Date.now() + forMs)); } catch(e){}
      }
    }

    if(shouldShow()){
      setTimeout(() => notice.classList.add('show-tb'), 900);
    }

    closeBtn.addEventListener('click', ()=>{
      const ms = dontShow.checked ? 24*60*60*1000 : 0;
      hide(ms);
    });

    document.addEventListener('keydown', (e)=>{
      if(e.key === 'Escape' && notice.classList.contains('show-tb')){
        const ms = dontShow.checked ? 24*60*60*1000 : 0;
        hide(ms);
      }
    });

    notice.addEventListener('click', (e)=>{
      if(e.target === notice){
        const ms = dontShow.checked ? 24*60*60*1000 : 0;
        hide(ms);
      }
    });
  })();