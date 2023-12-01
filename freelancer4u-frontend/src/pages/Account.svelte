<script>
	import { isAuthenticated, user } from "../store";

</script>

<h1 class="mt-3">Account Details</h1        >
<p>Name: {$user.name}</p>
<p>Nickname: {$user.nickname}</p>
<p>E-Mail: {$user.email}</p>
{#if $isAuthenticated}
<!-- other parts of account page -->
<!-- Show roles only if user has at least one role -->
{#if $user.user_roles && $user.user_roles.length > 0}
<p><b>Roles:</b> {$user.user_roles}</p>
{/if}
{:else}
<p>Not logged in</p>
{/if}