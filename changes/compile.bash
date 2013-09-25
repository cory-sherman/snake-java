#!/bin/bash
cd `dirname "$0"`

cl='CHANGELOG.md'
tmp='CHANGELOG.md.tmp'

head -n 3 "$cl" > "$tmp"

echo -n `date +%Y-%m-%d` >> "$tmp"
if [ "a$@" != 'a' ]
then
  echo -n " _Version $@_" >> "$tmp"
fi
echo >> "$tmp"

echo '---' >> "$tmp"
for file in *.change; do
  if [ -e "$file" ]; then
    echo -n '* ' >> "$tmp"
    cat "$file" >> "$tmp"
    rm "$file"
  fi
done

sed '1,2d' "$cl" >> "$tmp"
mv "$tmp" "$cl"
