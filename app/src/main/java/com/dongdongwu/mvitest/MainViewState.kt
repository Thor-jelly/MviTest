package com.dongdongwu.mvitest

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/14 10:28 <br/>
 */
data class MainViewState(
    val pageState: PageState,
    val list: List<String> = emptyList()
)

sealed class PageState {
    object EMPTY : PageState()
    object CONTENT : PageState()
}

sealed class MainViewEvent {
    data class ShowSnackbar(val msg: String) : MainViewEvent()
    data class ShowToast(val msg: String) : MainViewEvent()
}

sealed class MainViewAction {
    data class ItemClicked(val id: String) : MainViewAction()
    object RefreshData : MainViewAction()
    object LoadMoreData : MainViewAction()
}