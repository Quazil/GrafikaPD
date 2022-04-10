def make_bezier(xys):
    n = len(xys)
    kombinacje = pascal_row(n - 1)

    def bezier(a):
        result = []
        for t in a:
            b = (t ** i for i in range(n))
            c = reversed([(1 - t) ** i for i in range(n)])
            d = [c * a * b for c, a, b in zip(kombinacje, b, c)]
            result.append(
                tuple(sum([e * p for e, p in zip(d, ps)]) for ps in zip(*xys)))
        return result

    return bezier


def pascal_row(n):
    result = [1]
    x, numerator = 1, n
    for denominator in range(1, n // 2 + 1):
        x *= numerator
        x /= denominator
        result.append(x)
        numerator -= 1
    if n & 1 == 0:
        result.extend(reversed(result[:-1]))
    else:
        result.extend(reversed(result))
    return result

from PIL import Image, ImageDraw


if __name__ == '__main__':

    im = Image.new('RGBA', (100, 100), (0, 0, 0, 0))
    draw = ImageDraw.Draw(im)
    ts = [t / 100.0 for t in range(101)]

    xys = [(10, 80), (25, 0), (50, 80)]
    bezier = make_bezier(xys)
    punkty = bezier(ts)

    xys = [(50, 80), (75, 0), (100, 80)]
    bezier = make_bezier(xys)
    punkty.extend(bezier(ts))

    xys = [(0, 50), (0, 30)]
    bezier = make_bezier(xys)
    punkty.extend(bezier(ts))

    draw.polygon(punkty, fill=None, outline='white')
    im.save('LiteraM.png')