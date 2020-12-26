import store from './store';

export function fireConfirmPopUp(message){
      const popUpBackground = document.createElement('div');
      popUpBackground.className = 'pop-up-background';
      document.body.appendChild(popUpBackground);

      const popUp = document.createElement('div');
      popUp.className = 'pop-up';
      popUpBackground.appendChild(popUp);

      const msg = document.createElement('h2');
      msg.innerText = message;
      popUp.appendChild(msg);

      const cancelBtn = document.createElement('span');
      cancelBtn.innerText = 'Cancel';
      cancelBtn.className = 'pop-up-close-button cancel-btn';

      const confrimBtn = document.createElement('span');
      confrimBtn.innerText = 'Confirm';
      confrimBtn.className = 'pop-up-close-button confrim-btn';   

      cancelBtn.onclick = () => {    
        store.commit('setConfirm', false);
        document.body.removeChild(popUpBackground); 
      }

      confrimBtn.onclick = () => {
        store.commit('setConfirm', true);
        document.body.removeChild(popUpBackground); 
      }

      popUp.appendChild(cancelBtn);
      popUp.appendChild(confrimBtn);
}


export function fireInfoPopUp(message){
    const popUpBackground = document.createElement('div');
    popUpBackground.className = 'pop-up-background';
    document.body.appendChild(popUpBackground);

    const popUp = document.createElement('div');
    popUp.className = 'pop-up';
    popUpBackground.appendChild(popUp);

    const msg = document.createElement('h2');
    msg.innerText = message;
    popUp.appendChild(msg);

    const confrimBtn = document.createElement('span');
    confrimBtn.innerText = 'Ok';
    confrimBtn.className = 'pop-up-close-button ok-btn';   

    confrimBtn.onclick = () => {
      document.body.removeChild(popUpBackground); 
    }

    popUp.appendChild(confrimBtn);
}