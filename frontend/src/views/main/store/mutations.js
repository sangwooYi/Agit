export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function setJWTToken (state, token) {
  state.JWTToken = token
}

export function setJWTTokenReset (state) {
  state.JWTToken = null
}

export function setUserTeam (state, team) {
  state.userTeams = team
}

export function setIsChatPanel(state, value){
  state.isChatPanel = value;
}

export function setMessages(state, data){
  state.messages.push(data);
}

export function clearMessages(state){
  state.messages = [];
}

export function setSecretName(state, value){
  state.scretName = value
}

export function setChattingRoomId(state, value){
  state.chattingRoomId = value
}

export function setNickName (state, value) {
  state.nickName = value
}
