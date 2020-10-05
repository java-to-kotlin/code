package travelator.mobile

class Application(
    private var preferences: UserPreferences // <1>
) {
    fun showWelcome() {
        WelcomeView(preferences).show()
    }

    fun editPreferences() {
        preferences = PreferencesView(preferences).showModal()
    }
}