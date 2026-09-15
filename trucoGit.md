git config --global alias.acp '!f() { git add . && git commit -m "$1" && git push; }; f'


git acp "[entregar texto del comit]"
